package com.omar.product_reviews.controllers;

import com.omar.product_reviews.dtos.request.RegisterRequestDTO;
import com.omar.product_reviews.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        authService.registerUser(registerRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
