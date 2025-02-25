package com.omar.product_reviews.controllers;

import com.omar.product_reviews.dtos.request.LoginRequestDTO;
import com.omar.product_reviews.dtos.request.RegisterRequestDTO;
import com.omar.product_reviews.dtos.response.LoginResponseDTO;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.infra.security.TokenService;
import com.omar.product_reviews.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        authService.registerUser(registerRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.generateToken((User) authenticate.getPrincipal());
        return ResponseEntity.ok(authService.loginUser(loginRequestDTO, token));
    }
}
