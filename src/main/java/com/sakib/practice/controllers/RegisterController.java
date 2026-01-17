package com.sakib.practice.controllers;

import com.sakib.practice.modals.UserDto;
import com.sakib.practice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public RegisterController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("userDto" , new UserDto());

        return "register";
    }

    @PostMapping("/register/user")
    public String registration(@ModelAttribute UserDto userDto){

        System.out.println("I get it");

        if(!userDto.getPassword().isEmpty()){
            System.out.println("Get User Information from registration page");

            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userService.addNewUser(userDto);

            System.out.println("User added successfully");

        }

        return "redirect:/login?sucess=true";
    }
}
