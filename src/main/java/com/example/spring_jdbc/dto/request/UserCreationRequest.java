package com.example.spring_jdbc.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
     String id;
     @Size(min = 3, message = "ten min 3 ki tự")
     String username;

    @Size(min = 7, message = "it nhat 7 tu")
    String password;
    @Size(min = 7, message = "it nhat 7 tu ")
    String firstName;

    @Size(min = 7, max = 15, message = "du kis tu")
    String lastName;

    LocalDate dob;

}