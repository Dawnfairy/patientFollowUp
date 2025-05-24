package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.Doctor;

public interface AppointmentGenerationService {
    void generateDefaultAppointmentsForDoctor(Doctor doctor);
}
