package com.example.spring_jdbc.repository;

import com.example.spring_jdbc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
boolean existsByUsername(String username);

    User findByUsername(String userName);

}
