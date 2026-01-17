package com.sakib.practice.repositories;

import com.sakib.practice.modals.UserDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends MongoRepository<UserDto, String> {
    Optional<UserDto> findByEmail(String email);
    Optional<UserDto> findByPhoneNumber(String phoneNumber);
}
