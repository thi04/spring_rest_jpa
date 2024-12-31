package com.example.spring_jdbc.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
   int code=1000;
    String messenger;
     T result;


}
