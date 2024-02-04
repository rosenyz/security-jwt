package com.roseny.securityjwt.service;

import com.roseny.securityjwt.model.Email;
import com.roseny.securityjwt.model.User;
import com.roseny.securityjwt.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {
    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final UserService userService;

    public void sendEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rosenywqew@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

        log.info("Mail sent successfully!");
    }

    public ResponseEntity<?> confirmUserByEmail(UUID emailUUID) {
        Email email = emailRepository.findById(emailUUID).orElse(null);

        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ссылка не активна :(");
        }

        User user = email.getUser();

        if (user.getEmailConfirmed()) {
            emailRepository.delete(email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Вы уже подтвердили свой email.");
        }

        user.setEmailConfirmed(true);
        userService.save(user);

        emailRepository.delete(email);
        return ResponseEntity.ok("Вы успешно подтвердили свой email.");
    }

    public void save(Email email) {
        emailRepository.save(email);
    }
}
