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

    public SecurityConfig(HospuserDetailsService hospuserDetailsService) {
        this.hospuserDetailsService = hospuserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/public/**").permitAll()
//                        .requestMatchers("/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers("/hospusers/", "/hospuser/**" ).hasAuthority("ROLE_SECRETARY")
//                        .requestMatchers( "/patientvisits/**", "/patientvisits/add/**", "/patientvisits/").hasAuthority("ROLE_DOCTOR")
                        .anyRequest().authenticated()
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
        System.out.println(passwordEncoder().encode("newpass"));
        builder.userDetailsService(hospuserDetailsService).passwordEncoder(passwordEncoder());
        return builder.build();
    }


}
