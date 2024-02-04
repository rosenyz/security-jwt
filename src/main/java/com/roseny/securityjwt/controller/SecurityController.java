package com.roseny.securityjwt.controller;

import com.roseny.securityjwt.component.JwtCore;
import com.roseny.securityjwt.dto.LoginDto;
import com.roseny.securityjwt.dto.RegisterDto;
import com.roseny.securityjwt.model.Email;
import com.roseny.securityjwt.model.User;
import com.roseny.securityjwt.service.EmailSenderService;
import com.roseny.securityjwt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class SecurityController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final EmailSenderService emailSenderService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getUsername(), loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtCore.generateToken(userService.loadUserByUsername(loginDto.getUsername()));
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        if (userService.existsUserByUsername(registerDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this name is already registered");
        }
        User user = new User();
        Email email = new Email();

        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        String hashed = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(hashed);

        userService.save(user);

        email.setUser(user);
        emailSenderService.save(email);

        emailSenderService.sendEmail(registerDto.getEmail(), "Security-JWT", "Добро пожаловать, %s. Вы успешно зарегистрировались. Подтвердите свою учетную запись http://localhost:8080/api/email/confirm?uuid=%s".formatted(user.getUsername(), email.getUuid()));

        return ResponseEntity.ok("Successfully registered");
    }
}
