package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.Appointment;

public interface AppointmentApprovalService {
    Appointment approve(Long appointmentId);
    Appointment reject(Long appointmentId);
}
