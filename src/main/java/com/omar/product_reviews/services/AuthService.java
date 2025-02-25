package com.omar.product_reviews.services;

import com.omar.product_reviews.dtos.request.LoginRequestDTO;
import com.omar.product_reviews.dtos.request.RegisterRequestDTO;
import com.omar.product_reviews.dtos.response.LoginResponseDTO;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.exceptions.UserAlreadyExistsException;
import com.omar.product_reviews.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void registerUser(@Valid RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByEmail(registerRequestDTO.email()).isPresent()){
            throw new UserAlreadyExistsException(String.format("User with email: %s, already exists", registerRequestDTO.email()));
        }

        String password = new BCryptPasswordEncoder().encode(registerRequestDTO.password());

        User user = User.builder().firstName(registerRequestDTO.firstName())
                .lastName(registerRequestDTO.lastName()).email(registerRequestDTO.email())
                .password(password).build();
        userRepository.save(user);
    }

    public LoginResponseDTO loginUser(@Valid LoginRequestDTO loginRequestDTO, String token) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequestDTO.email());
        if (userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("User with email: %s, not found", loginRequestDTO.email()));
        }

        return new LoginResponseDTO(loginRequestDTO.email(), token);
    }
}
