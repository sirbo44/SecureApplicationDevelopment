package com.nyc.hosp.controller;

import com.nyc.hosp.domain.Hospuser;
import com.nyc.hosp.repos.HospuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.*;

import java.time.ZoneOffset;
import java.util.Enumeration;
import java.util.Optional;


@Controller
public class HomeController {
    @Autowired
    HospuserRepository hospuserRepository;

    @GetMapping("/")
    public String index() {
        OffsetDateTime offsetDateTime = LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Optional<Hospuser> user = hospuserRepository.findByUsername(authentication.getName());
        if (user.isPresent()) {
            OffsetDateTime lastChange = user.get().getLastchangepassword();
            if (lastChange == null) {
                // Handle null case, e.g., force password change
                return "redirect:/change_password";
            }
            Duration duration = Duration.between(lastChange, offsetDateTime);
            if (duration.toDays() > 30) {
                return "redirect:/change_password";
            } else {
                return "home/index";
            }
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/change_password")
    public String showChangePasswordPage() {
        return "home/change_password";
    }

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/change_password")
    public String changePassword(String userpassword, String userpassword2) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Optional<Hospuser> user = hospuserRepository.findByUsername(authentication.getName());
        if (user.isPresent()) {
            user.get().setLastchangepassword(LocalDate.now().atStartOfDay().atOffset(ZoneOffset.UTC));
            user.get().setUserpassword(passwordEncoder.encode(userpassword));
            hospuserRepository.save(user.get());
        }
        return "redirect:/";
    }

}
