package com.omar.product_reviews.controllers;

import com.omar.product_reviews.dtos.request.LoginRequestDTO;
import com.omar.product_reviews.dtos.request.RegisterRequestDTO;
import com.omar.product_reviews.dtos.response.LoginResponseDTO;
import com.omar.product_reviews.entities.User;
import com.omar.product_reviews.infra.handler.ErrorMessage;
import com.omar.product_reviews.infra.security.TokenService;
import com.omar.product_reviews.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Tag(name = "AuthController", description = "These endpoints are responsible for register and login an user")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Operation(summary = "This endpoint is responsible for register an user",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation!"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        authService.registerUser(registerRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "This endpoint is responsible for login an user",
            method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation!"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content())
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.generateToken((User) authenticate.getPrincipal());
        return ResponseEntity.ok(authService.loginUser(loginRequestDTO, token));
    }
}
