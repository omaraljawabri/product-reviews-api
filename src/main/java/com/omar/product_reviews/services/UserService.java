package com.omar.product_reviews.services;

import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with id: %d, not found", id)));
    }
}
