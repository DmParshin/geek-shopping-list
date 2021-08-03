package com.example.geekshoppinglist.service;

import com.example.geekshoppinglist.entity.User;
import com.example.geekshoppinglist.repository.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepostory repostory;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepostory repostory, BCryptPasswordEncoder passwordEncoder) {
        this.repostory = repostory;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(UserRepr userRepr){
        User user = new User();
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));
        repostory.save(user);
    }
}
