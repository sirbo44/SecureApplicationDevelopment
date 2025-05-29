package com.nyc.hosp.controller;

import com.nyc.hosp.domain.Hospuser;
import com.nyc.hosp.repos.HospuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class LoginController {
    HospuserRepository hospuserRepository;


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/error-message")
    public String showErrorPage() {
        return "error";
    }

    @GetMapping("/change-password")
    public String showChangePasswordPage() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(String userpassword, String userpassword2) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Optional<Hospuser> user = hospuserRepository.findByUsername(authentication.getName());
        user.get().setLastchangepassword((OffsetDateTime) LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC));
        user.get().setUserpassword(userpassword);
        return "redirect:/";
    }
}
