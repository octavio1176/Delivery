package com.example.security.service;

import com.example.security.domain.LoginDto.ForgotPasswordRequest;
import com.example.security.domain.LoginDto.LoginRequestDTO;
import com.example.security.domain.LoginDto.LoginResponseDTO;
import com.example.security.domain.LoginDto.VerificationCode;
import com.example.security.domain.UserDto.ResetPasswordRequest;
import com.example.security.domain.UserDto.UserRequest;
import com.example.security.domain.entity.User;
import com.example.security.domain.entity.UserStaus;
import com.example.security.domain.repository.UserRepository;
import com.example.security.exception.CodeNotFoundException;
import com.example.security.exception.EmailAlreadyExistsException;
import com.example.security.exception.UserNotFoundException;
import com.example.security.security.JwtService;
import com.example.security.util.RandomString;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UserService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final Map<String, UserRequest> pendingUsers = new ConcurrentHashMap<>();
    private final Map<String, VerificationCode> verificationCodes = new ConcurrentHashMap<>();
    private final Map<String, VerificationCode> passwordResetCodes = new ConcurrentHashMap<>();

    private static final long CODE_EXPIRATION_MINUTES = 10;

    public UserService(EmailService emailService, UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public void register(UserRequest userRequest) throws MessagingException
    {

        if (userRepository.findUsersByEmail(userRequest.email()).isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        String code = RandomString.generateNumericCode();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES);

        pendingUsers.put(userRequest.email(), userRequest);
        verificationCodes.put(userRequest.email(), new VerificationCode(code, expiresAt));

        emailService.sendConfirmationEmail(userRequest.email(), code);
    }

    public LoginResponseDTO confirmCode(String email, String code)
    {

        VerificationCode saved = verificationCodes.get(email);

        if (saved == null) {
            throw new RuntimeException("Code  not found .");
        }

        if (saved.isExpired()) {
            verificationCodes.remove(email);
            pendingUsers.remove(email);
            throw new RuntimeException("code expired . ask for another one.");
        }

        if (!saved.code().equals(code)) {
            throw new RuntimeException("invalid code .");
        }

        UserRequest request = pendingUsers.get(email);

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setUserStaus(UserStaus.USER);
        user.setEnabled(true);

        userRepository.save(user);

        verificationCodes.remove(email);
        pendingUsers.remove(email);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtService.generateToken(userDetails);

        user.setToken(token);
        userRepository.save(user);

        return new LoginResponseDTO(token);
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO)
    {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));

        User user = (User) authentication.getPrincipal();

        assert user != null;
        String token = jwtService.generateToken(user);

        user.setToken(token);
        userRepository.save(user);

        return new LoginResponseDTO(token);
    }

    public void forgotPassword(ForgotPasswordRequest request)
    {

        User user = userRepository.findUsersByEmail(request.email()).
                orElseThrow(UserNotFoundException::new);

        String code = RandomString.generateNumericCode();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(CODE_EXPIRATION_MINUTES);

        passwordResetCodes.put(user.getEmail(), new VerificationCode(code, expiresAt));

        emailService.sendCode(user.getEmail(), code);
    }

    public void resetPassword(ResetPasswordRequest request)
    {

        VerificationCode saved = passwordResetCodes.get(request.email());

        if (saved == null)
        {
            throw new CodeNotFoundException();
        }

        if (saved.isExpired())
        {
            passwordResetCodes.remove(request.email());
            throw new IllegalStateException("code expired . ask for another one");
        }

        if (!saved.code().equals(request.code()))
        {
            throw new IllegalStateException("invalid Code.");
        }

        User user = userRepository.findUsersByEmail(request.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found "));

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setToken(null);
        userRepository.save(user);

        passwordResetCodes.remove(request.email());
    }
    public void logout(){
        User user =  (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        assert user != null;
        userRepository.delete(user);
    }

}