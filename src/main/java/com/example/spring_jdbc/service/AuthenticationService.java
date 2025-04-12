package com.example.spring_jdbc.service;
import com.example.spring_jdbc.dto.request.AuthenticationRequest;
import com.example.spring_jdbc.dto.request.IntrospectRequest;
import com.example.spring_jdbc.dto.response.AuthenticationResponse;
import com.example.spring_jdbc.dto.response.IntrospectResponse;
import com.example.spring_jdbc.entity.User;
import com.example.spring_jdbc.exception.AppException;
import com.example.spring_jdbc.exception.ErrorCode;
import com.example.spring_jdbc.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.PrimitiveIterator;
import java.util.StringJoiner;


@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor

public class AuthenticationService {
    UserRepository userRepository;
@NonFinal
    protected static  final String Singer_key="ABxsW1f93OGchGrVwrhVRiNpBZnN9/SB8Pl0rgTHVlLh3Mx9rt+/2d4Z9H/x3too";
//INTROSPECT
    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException,ParseException {

        var token=request.getToken();

        JWSVerifier verifier=new MACVerifier(Singer_key.getBytes()) ;
        SignedJWT signedJWT=SignedJWT.parse(token);

        Date exirytime=signedJWT.getJWTClaimsSet().getExpirationTime();
        var verify   =signedJWT.verify(verifier);
        return IntrospectResponse.builder()

                .valid(verify && exirytime.after(new Date()))
                .build();



    }








// AUTHENTICATION
   public AuthenticationResponse authenticate(AuthenticationRequest request) {
       var user = userRepository.findByUsername(request.getUsername())
               .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
       PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
       boolean authenticated = passwordEncoder.matches(request.getPassword(),
               user.getPassword());



// đăng nhập có thanhfc ông hay ko ??
       if (!authenticated)
           throw new AppException(ErrorCode.UTHENTICATION);
           var token=generateToken(user);
       return AuthenticationResponse.builder()
               .token(token)
        .authenticated(true)
        .build();


   }

    private String generateToken(   User user) {
        JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()

                .subject(user.getUsername())
                .issuer("pham_van_thi.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plusSeconds(3600).toEpochMilli()
                ))
                .claim("scope",buildScope(user))

                .build();
        Payload payload=new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject=new JWSObject(header,payload);
        // kí token
        try {
            jwsObject.sign(new MACSigner(Singer_key.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("cannot creat token",e);
            throw new RuntimeException(e);
        }



        }
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" "); // dùng khoảng trắng làm dấu phân cách
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> stringJoiner.add(role));
        }
        return stringJoiner.toString();
    }



    }
