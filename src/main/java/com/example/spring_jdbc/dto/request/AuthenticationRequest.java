package com.example.spring_jdbc.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuthenticationRequest {
            Long id;
        String username;
        String password;

    }


