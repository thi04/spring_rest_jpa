package com.example.spring_jdbc.mapper;

import com.example.spring_jdbc.dto.request.UserCreationRequest;
import com.example.spring_jdbc.dto.request.UserUpdateRequest;
import com.example.spring_jdbc.dto.response.UserResponse;
import com.example.spring_jdbc.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest request);

//    @Mapping(source = "firstName",target = "lastName")

    UserResponse toUserResponse(User user);

void updateUser(@MappingTarget  User user, UserUpdateRequest request);


}
