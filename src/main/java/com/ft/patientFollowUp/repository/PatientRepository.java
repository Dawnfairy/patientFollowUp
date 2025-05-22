package com.ft.patientFollowUp.repository;

import com.ft.patientFollowUp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    // Gerekirse özel sorgular ekleyebilirsin
}
