//package com.sakib.practice.securityConfig;
//
//import com.sakib.practice.modals.UserDto;
//import com.sakib.practice.service.UserService;
//import org.jspecify.annotations.Nullable;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    public CustomAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//
//    @Override
//    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        String username = authentication.getName();
//        String password =  authentication.getCredentials().toString();
//
//        UserDto userByPhoneNumber = userService.getUserByPhoneNumber(username);
//        UserDto userByEmail =  userService.getUserByEmail(username);
//
//        System.out.println("I am in Custom Authentication provider");
//
//        if(userByEmail == null && userByPhoneNumber == null){
//            System.out.println("User not found");
//            throw  new BadCredentialsException("User not found");
//        }
//
//        if(userByEmail != null){
//            if(passwordEncoder.matches(password , userByEmail.getPassword())){
//                return  new UsernamePasswordAuthenticationToken(username , null , List.of(
//                        new SimpleGrantedAuthority("ROLE_" + "ADMIN")
//                ));
//            } else{
//                System.out.println("Invalid user name and password");
//                throw new BadCredentialsException("Invalid Username or password");
//            }
//        } else{
//            if(passwordEncoder.matches(password , userByPhoneNumber.getPassword())){
//                return new UsernamePasswordAuthenticationToken(username , null , List.of(
//                        new SimpleGrantedAuthority("ROLE_" + "ADMIN")
//                ));
//            } else{
//                System.out.println("Invalid user name and password");
//                throw new BadCredentialsException("Invalid Username or password");
//            }
//        }
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
