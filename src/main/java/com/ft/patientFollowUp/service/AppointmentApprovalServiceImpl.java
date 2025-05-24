package com.ft.patientFollowUp.service;

import com.ft.patientFollowUp.model.Appointment;
import com.ft.patientFollowUp.model.AppointmentStatus;
import com.ft.patientFollowUp.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentApprovalServiceImpl implements AppointmentApprovalService {
    private final AppointmentRepository repo;

    @Override
    public Appointment approve(Long id) {
        Appointment ap = repo.findById(id).orElseThrow();
        if (ap.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("No pending request");
        }
        ap.setStatus(AppointmentStatus.APPROVED);
        return repo.save(ap);
    }

    @Override
    public Appointment reject(Long id) {
        Appointment ap = repo.findById(id).orElseThrow();
        if (ap.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("No pending request");
        }
        ap.setStatus(AppointmentStatus.REJECTED);
        return repo.save(ap);
    }
}
