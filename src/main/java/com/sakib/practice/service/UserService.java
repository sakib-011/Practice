package com.sakib.practice.service;

import com.sakib.practice.modals.UserDto;
import com.sakib.practice.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addNewUser(UserDto userDto){
        userRepository.save(userDto);

        return true;
    }

    public UserDto getUserByEmail(String email){
        return  userRepository.findByEmail(email).orElse(null);
    }

    public UserDto getUserByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }


}
