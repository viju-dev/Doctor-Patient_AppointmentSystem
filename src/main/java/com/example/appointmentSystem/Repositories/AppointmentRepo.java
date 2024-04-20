package com.example.appointmentSystem.Repositories;

import com.example.appointmentSystem.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment,Integer> {
List<Appointment> findAppointmentsByDoctorIdAndDate(int doctorId, LocalDate now);

}
