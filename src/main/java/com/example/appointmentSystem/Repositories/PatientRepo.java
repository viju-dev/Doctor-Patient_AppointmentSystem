package com.example.appointmentSystem.Repositories;

import com.example.appointmentSystem.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepo extends JpaRepository<Patient,Integer> {
}
