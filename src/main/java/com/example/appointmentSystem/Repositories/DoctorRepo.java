package com.example.appointmentSystem.Repositories;

import com.example.appointmentSystem.Entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor,Integer> {
}
