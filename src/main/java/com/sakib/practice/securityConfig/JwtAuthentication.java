package com.sakib.practice.securityConfig;

import com.sakib.practice.service.JwtService;
import com.sakib.practice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Password;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthentication extends OncePerRequestFilter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public JwtAuthentication(PasswordEncoder passwordEncoder, UserService userService, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = null;
        Cookie[] cookies = request.getCookies();

        // CRITICAL FIX: Check if cookies array is not null before iterating
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break; // Exit loop once we find the JWT cookie
                }
            }
        }

        if (token == null) {
            SecurityContextHolder.clearContext();
        } else {
            try {
                Claims claims = jwtService.parseToken(token);
                String username = jwtService.getUsername(token);
                String role = jwtService.getRole(token);

                System.out.println("User name: " + username);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Log error but don't throw - just clear the context and continue
                System.err.println("JWT parsing failed: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }

        // CRITICAL: Always call filterChain.doFilter - this was missing in your else block!
        filterChain.doFilter(request, response);
    }

}
