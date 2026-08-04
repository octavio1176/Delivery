package com.example.security.controller;

import com.example.security.domain.LoginDto.ForgotPasswordRequest;
import com.example.security.domain.LoginDto.LoginRequestDTO;
import com.example.security.domain.LoginDto.LoginResponseDTO;
import com.example.security.domain.UserDto.ConfirmationRequest;
import com.example.security.domain.UserDto.ResetPasswordRequest;
import com.example.security.domain.UserDto.UserRequest;
import com.example.security.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/API")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRequest userRequest) throws MessagingException {
        userService.register(userRequest);
        return ResponseEntity.ok("Confirmation Code has been sent.");
    }

    @PostMapping("/confirmation")
    public ResponseEntity<LoginResponseDTO> confirmCode(@RequestBody @Valid ConfirmationRequest request) {
        LoginResponseDTO response = userService.confirmCode(request.email(), request.code());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = userService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) throws MessagingException {
        userService.forgotPassword(request);
        return ResponseEntity.ok("Recovery code has been sent .");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok("password updated .");
    }
    @DeleteMapping
    public void delete(){
        userService.logout();
    }
}