package com.sakib.practice.controllers;


import com.sakib.practice.modals.LoginDto;
import com.sakib.practice.modals.UserDto;
import com.sakib.practice.service.JwtService;
import com.sakib.practice.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(JwtService jwtService, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginDto" ,  new LoginDto());
        return "login";
    }

    @PostMapping("/login/user")
    public String login(@ModelAttribute LoginDto loginDto , HttpServletResponse response , Model model){

        model.addAttribute("loginDto" , new LoginDto());

        UserDto userDtoByEmail = userService.getUserByEmail(loginDto.getUsername());
        UserDto userDtoByPhoneNumber = userService.getUserByPhoneNumber(loginDto.getUsername());

        if(userDtoByEmail != null){
            if(!passwordEncoder.matches(loginDto.getPassword() , userDtoByEmail.getPassword())){
                return "login";
            }
        } else {
            if(!passwordEncoder.matches(loginDto.getPassword() , userDtoByPhoneNumber.getPassword())){
                return "login";
        }
        }

        String token =  jwtService.generateToken(loginDto.getUsername());
        Cookie cookie = new Cookie("JWT" ,  token);

        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return "redirect:/index";
    }
}
