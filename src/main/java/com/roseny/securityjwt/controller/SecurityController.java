package com.roseny.securityjwt.controller;

import com.roseny.securityjwt.component.JwtCore;
import com.roseny.securityjwt.dto.LoginDto;
import com.roseny.securityjwt.dto.RegisterDto;
import com.roseny.securityjwt.model.User;
import com.roseny.securityjwt.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class SecurityController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtCore jwtCore;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(), loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsUserByUsername(registerDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this name is already registered");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        String hashed = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(hashed);
        userRepository.save(user);
        return ResponseEntity.ok("Successfully registered");
    }
}
