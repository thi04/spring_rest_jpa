package com.example.spring_jdbc.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserResponse {

    Long id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}