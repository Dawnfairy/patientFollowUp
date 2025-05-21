package com.ft.patientFollowUp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppointmentStatus {
    PENDING,
    APPROVED,
    REJECTED
}