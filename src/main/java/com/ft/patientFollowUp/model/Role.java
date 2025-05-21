package com.ft.patientFollowUp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_PATIENT,
    ROLE_DOCTOR;

    @Override
    public String getAuthority() {
        return name();
    }
}