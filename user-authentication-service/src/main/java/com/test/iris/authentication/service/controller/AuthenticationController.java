package com.test.iris.authentication.service.controller;

import com.test.iris.authentication.service.entity.AuthRequest;
//import com.test.iris.authentication.service.entity.UserCredentials;
import com.test.iris.authentication.service.entity.Users;
import com.test.iris.authentication.service.request.UserRequest;
import com.test.iris.authentication.service.response.TokenResponse;
import com.test.iris.authentication.service.response.UserResponse;
import com.test.iris.authentication.service.response.ValidateTokenResponse;
import com.test.iris.authentication.service.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Register a new user", description = "Register a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> addNewUser(@RequestBody @Valid UserRequest userRequest){
        logger.info("UserRequest request recieved  {} : ",userRequest);
        UserResponse userResponse= authenticationService.saveUser(userRequest);
        logger.info("User saved successfully {} : ",userResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    @Operation(summary = "Generate authentication token", description = "Generate a JWT token for authenticated users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@RequestBody @Valid  AuthRequest authRequest){
        logger.info("AuthRequest request recieved  {} : ",authRequest);
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            logger.info("User Authenticated  {} : ",authRequest.getUsername());
            return authenticationService.generateToken(authRequest.getUsername());
        }   else {
            logger.info("User not Authenticated  {} : ",authRequest.getUsername());
            throw new RuntimeException("Invalid Username or Password");
        }

    }
    @Operation(summary = "Validate authentication token", description = "Validate a JWT token for authenticated users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "401", description = "Invalid token")
    })
    @GetMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> validateToken(@RequestParam("token") @NotNull String token){
        logger.info("Token validation recieved  {} : ");
        return  authenticationService.validateToken(token);

    }
}
