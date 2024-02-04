package com.roseny.securityjwt.controller;

import com.roseny.securityjwt.service.EmailSenderService;
import com.roseny.securityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailSenderService emailSenderService;

    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam(name = "uuid") UUID emailUUID) {
        return emailSenderService.confirmUserByEmail(emailUUID);
    }

}
