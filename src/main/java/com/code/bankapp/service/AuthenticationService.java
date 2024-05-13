package com.code.bankapp.service;

import com.code.bankapp.config.JwtService;
import com.code.bankapp.model.*;
import com.code.bankapp.exception.ConflictException;
import com.code.bankapp.exception.NoSuchException;
import com.code.bankapp.repository.UserRepository;
import com.code.bankapp.util.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final EmailUtil emailUtil;

    public String registerUser(RegisterRequest request) throws ConflictException {
        var userExist = userRepository.findByEmail(request.getEmail());
        if (userExist.isEmpty()) {
            User user = User.builder()
                    .firstName(request.getFirstname())
                    .lastName(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .active(false)
                    .build();
            userRepository.save(user);
            var token = jwtService.generateToken(user);
            try {
                emailUtil.sendUserTokenEmail(request.getEmail(), token);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return "Account created... check your email " + request.getEmail()
                    + " to verify and activate your account within 24hrs";
        }
        throw new ConflictException("User with email " + request.getEmail() + " already exists");
    }

    public String activateAccount(String email, String token) throws NoSuchException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchException("User not found"));
        user.setActive(true);
        userRepository.save(user);
        return "account activated...you can now login";
    }

    public String regenarateToken(String email) throws NoSuchException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchException("User not found"));
        var token = jwtService.generateToken(user);
        try {
            emailUtil.sendUserTokenEmail(email, token);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "check your email " + email + " token to activate account sent";
    }

    public AuthenticationResponse loginUser(LoginRequest request) throws NoSuchException, ConflictException {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchException("user with email " + request.getEmail() + " not found"));
        if (user.isActive()) {
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
                var token = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .role(user.getRole())
                        .message(request.getEmail() + " login successfully...")
                        .token(token)
                        .build();

            }
            throw new ConflictException("wrong password");
        }
        throw new ConflictException("account not active...");

    }

    public String forgotPassword(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var token = jwtService.generateToken(user);
        try {
            emailUtil.sendUForgotTokenEmail(email, token);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "check your email " + email + " token to reset password sent";
    }

    public String resetPassword(PasswordReset reset, String email, String token) throws ConflictException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (reset.getNewPassword().equals(reset.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(reset.getNewPassword()));
            userRepository.save(user);
            return "password reset successfully...you can login";
        }
        throw new ConflictException("password did not match...try again");
    }


    public String updateUserPassword(PasswordReset request, String email, User user)
            throws ConflictException, NoSuchException {
        var Updateduser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchException("User not found"));
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ConflictException("enter current password to change the password");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ConflictException("password did not match... try again");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "password updated successfully...";
    }

    public String registerAdmin(RegisterRequest request) throws ConflictException {
        var userExist = userRepository.findByEmail(request.getEmail());
        if (!userExist.isPresent()) {
            User user = User.builder()
                    .firstName(request.getFirstname())
                    .lastName(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ADMIN)
                    .active(true)
                    .build();
            userRepository.save(user);
            return "account created suceesfully....";
        }
        throw new ConflictException("User with email " + request.getEmail() + " already exists");
    }
}
