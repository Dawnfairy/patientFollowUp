package com.ft.patientFollowUp.repository;

import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser(AppUser user);
}
