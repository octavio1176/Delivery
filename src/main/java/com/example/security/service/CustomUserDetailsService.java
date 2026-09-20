package com.example.security.service;
import com.example.security.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    @NullMarked
    public  UserDetails loadUserByUsername(String email) {

       return   userRepository.findUsersByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found "));

    }
}
