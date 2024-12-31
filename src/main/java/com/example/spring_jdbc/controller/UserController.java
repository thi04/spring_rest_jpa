package com.example.spring_jdbc.controller;

import com.example.spring_jdbc.dto.request.ApiResponse;
import com.example.spring_jdbc.dto.request.UserCreationRequest;
import com.example.spring_jdbc.dto.request.UserUpdateRequest;
import com.example.spring_jdbc.dto.response.UserResponse;
import com.example.spring_jdbc.entity.User;
import com.example.spring_jdbc.exception.AppException;
import com.example.spring_jdbc.repository.UserRepository;
import com.example.spring_jdbc.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
//    @Autowired
    private  final   UserService userService;

    private final UserRepository userRepository;

    @PostMapping()
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request){
     ApiResponse<User>apiResponse=new ApiResponse<>();
     apiResponse.setResult(userService.createUser(request));

       return apiResponse;
    }

@GetMapping
    List<User>getUser(){
       return userService.getUser();
}

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable Long userId) {
       User user =   userRepository.findById(userId).get();
     return    UserResponse.builder().firstName(user.getFirstName()).build();
//        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }




}
