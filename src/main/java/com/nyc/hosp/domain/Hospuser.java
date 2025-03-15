package com.nyc.hosp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.OffsetDateTime;


@Entity
public class Hospuser {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String userpassword;

    @Column
    private OffsetDateTime lastlogondatetime;

    @Column
    private OffsetDateTime lastchangepassword;

    @Column(length = 100)
    private String email;

    @Column
    private boolean locked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(final String userpassword) {
        this.userpassword = userpassword;
    }

    public OffsetDateTime getLastlogondatetime() {
        return lastlogondatetime;
    }

    public void setLastlogondatetime(final OffsetDateTime lastlogondatetime) {
        this.lastlogondatetime = lastlogondatetime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public OffsetDateTime getLastchangepassword() {
        return lastchangepassword;
    }

    public void setLastchangepassword(OffsetDateTime lastchangepassword) {
        this.lastchangepassword = lastchangepassword;
    }
}
