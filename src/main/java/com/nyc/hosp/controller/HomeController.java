package com.nyc.hosp.controller;

import com.nyc.hosp.domain.Hospuser;
import com.nyc.hosp.domain.Role;
import com.nyc.hosp.repos.HospuserRepository;
import com.nyc.hosp.repos.RoleRepository;
import com.nyc.hosp.service.HospuserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
        Duration duration = Duration.between(user.get().getLastlogondatetime(), offsetDateTime);
        if (duration.toDays() > 30) {
            return "redirect:/change-password";
        } else {
            return "home/index";
        }


    }

}
