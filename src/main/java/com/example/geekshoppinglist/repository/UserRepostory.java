package com.example.geekshoppinglist.repository;

import com.example.geekshoppinglist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepostory extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
