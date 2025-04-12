package com.example.spring_jdbc.controller;

import com.example.spring_jdbc.dto.request.ApiResponse;
import com.example.spring_jdbc.dto.request.AuthenticationRequest;
import com.example.spring_jdbc.dto.request.IntrospectRequest;
import com.example.spring_jdbc.dto.response.AuthenticationResponse;
import com.example.spring_jdbc.dto.response.IntrospectResponse;
import com.example.spring_jdbc.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/authentication")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {
    AuthenticationService authenticationService;


    @PostMapping("/token")
     ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result) // Trả về trực tiếp mà không cần bọc lại
                .build();
    }



    @PostMapping("/introspect")
     ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result) // Trả về trực tiếp mà không cần bọc lại
                .build();
    }
}
