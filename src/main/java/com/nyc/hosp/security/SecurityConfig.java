package com.nyc.hosp.security;


import com.nyc.hosp.service.HospuserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final HospuserDetailsService hospuserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(HospuserDetailsService hospuserDetailsService, PasswordEncoder passwordEncoder) {
        this.hospuserDetailsService = hospuserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/public/**").permitAll()
                        .requestMatchers("/patientvisits/**").hasAnyAuthority("ROLE_DOCTOR","ROLE_ADMIN")
                        .requestMatchers("/hospusers/**").hasAnyAuthority("ROLE_SECRETARY","ROLE_ADMIN")
                        .requestMatchers("/roles/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SECRETARY", "ROLE_DOCTOR")
                )
                .formLogin(Customizer.withDefaults()) // default login page
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(hospuserDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }


}
