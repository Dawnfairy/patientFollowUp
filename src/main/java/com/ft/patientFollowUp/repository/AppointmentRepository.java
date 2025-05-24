package com.ft.patientFollowUp.repository;

import com.ft.patientFollowUp.model.Appointment;
import com.ft.patientFollowUp.model.AppointmentStatus;
import com.ft.patientFollowUp.model.Doctor;
import com.ft.patientFollowUp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Hasta görecek: sadece boş slot’lar
    List<Appointment> findByStatus(AppointmentStatus status);

    // Hasta kendi talepleri
    List<Appointment> findByPatient(Patient patient);

    // Doktor bekleyen talepleri
    List<Appointment> findByDoctorAndStatus(Doctor doctor, AppointmentStatus status);

    // Doktor tüm slot’ları
    List<Appointment> findByDoctor(Doctor doctor);

}
