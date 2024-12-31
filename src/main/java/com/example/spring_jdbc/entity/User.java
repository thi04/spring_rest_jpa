package com.example.spring_jdbc.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "jdbc_spring")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "username")
 String username;
    @Column(name = "password")
 String password;
    @Column(name = "firstname")
 String firstName;
    @Column(name = "lastname")
 String lastName;
    @Column(name = "dob")
 LocalDate dob;


}
