package com.code.bankapp.controller;

import com.code.bankapp.model.*;
import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/user/signup")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request)
            throws ConflictException {
        log.info("registering user : {}", request);
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> activateAccount(@RequestParam String email, @RequestParam String token)
            throws NoSuchException {
        log.info("user account activation : {}", email);
        return ResponseEntity.ok(authenticationService.activateAccount(email, token));
    }

    @PutMapping("/regenarate-token")
    public ResponseEntity<String> regenarateToken(@RequestParam String email) throws NoSuchException {
        log.info("generating token for : {}", email);
        return ResponseEntity.ok(authenticationService.regenarateToken(email));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginRequest request)
            throws ConflictException, NoSuchException {
        log.info("authenticating user : {}", request);
        return ResponseEntity.ok(authenticationService.loginUser(request));
    }

    @PutMapping("/forgot-Password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        log.info("token for password reset send  for : {}", email);
        return ResponseEntity.ok(authenticationService.forgotPassword(email));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody PasswordReset reset,
            @RequestParam String email,
            @RequestParam String token) throws ConflictException {
        log.info("reset password for " + email + "token " + token);
        return ResponseEntity.ok(authenticationService.resetPassword(reset, email, token));
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updateUserPassword
            (@Valid @RequestBody PasswordReset request,
             @AuthenticationPrincipal User user,
             @RequestParam String email) throws ConflictException, NoSuchException {
        user.getEmail();
        return ResponseEntity.ok(authenticationService.updateUserPassword(request, email, user));
    }
    /** Admin registration **/
    @PostMapping("/admin/signup")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterRequest request)
            throws ConflictException {
        log.info("registering user : {}", request);
        return ResponseEntity.ok(authenticationService.registerAdmin(request));
    }
}
