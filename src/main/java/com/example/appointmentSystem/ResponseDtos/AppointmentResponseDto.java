package com.example.appointmentSystem.ResponseDtos;

import com.example.appointmentSystem.Entities.Doctor;
import com.example.appointmentSystem.Entities.Patient;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentResponseDto {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime appointmentTime;
    private Boolean isBooked;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookedAt;
    private DoctorResponseDto doctor;
    private  PatientResponseDto patient;
}
