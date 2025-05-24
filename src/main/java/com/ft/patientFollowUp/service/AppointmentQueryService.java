package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.model.Appointment;
import com.ft.patientFollowUp.model.Doctor;

import java.util.List;

public interface AppointmentQueryService {
    List<Appointment> getAvailableAppointments();
    List<Appointment> getMyRequestedAppointments(AppUser user);
    List<Appointment> getPendingRequestsForDoctor(Doctor doctor);
    List<Appointment> getAllAppointmentsForDoctor(Doctor doctor);
}
