package com.example.geekshoppinglist.service;

import com.example.geekshoppinglist.repository.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepostory userRepostory;

    @Autowired
    public UserAuthService(UserRepostory userRepostory) {
        this.userRepostory = userRepostory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepostory.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("USER"))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}
