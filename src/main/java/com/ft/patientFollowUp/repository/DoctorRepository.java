package com.ft.patientFollowUp.repository;

import com.ft.patientFollowUp.model.AppUser;
import com.ft.patientFollowUp.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(AppUser user);
}