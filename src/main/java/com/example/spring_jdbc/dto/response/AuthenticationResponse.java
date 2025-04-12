package com.example.spring_jdbc.dto.response;


import com.example.spring_jdbc.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuthenticationResponse {
   String token;
    boolean authenticated;

    public AuthenticationResponse(String token) {
    }
}



