package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.model.Appointment;

public interface AppointmentRequestService {
    Appointment requestAppointment(Long appointmentId, AppUser user);

}
