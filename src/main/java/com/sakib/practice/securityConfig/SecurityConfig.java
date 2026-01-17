package com.sakib.practice.securityConfig;

import com.sakib.practice.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {




    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtAuthentication jwtAuthentication) throws Exception{

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(se-> se.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(re-> re
                .requestMatchers("/login" ,"/login/user" , "/" , "/css/**" , "/images/**"
                , "/js/**" , "/libs/**" , "/scss/**" , "/register" , "/register/**").permitAll()
                .requestMatchers("/index").authenticated()
                .anyRequest().authenticated())
                .addFilterBefore(jwtAuthentication , UsernamePasswordAuthenticationFilter.class);

        httpSecurity.formLogin(lf-> lf
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/index" , false)
                .failureUrl("/login?error"));

       return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        return username -> User.withUsername(username).password(" ").build();
//    }

}
