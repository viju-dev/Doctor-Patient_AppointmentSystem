package com.example.appointmentSystem.Services;

import com.example.appointmentSystem.Entities.Appointment;
import com.example.appointmentSystem.ResponseDtos.AppointmentResponseDto;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponseDto> createAppointmentSlots(int doctorId);

    AppointmentResponseDto bookAppointement(int appointmentId,int patientId);

    //get
    AppointmentResponseDto getAppointment(int appointmentId);

    //get available appointments slots
    List<AppointmentResponseDto> getAvailableAppointmentsByDoctor(int doctorId);

    //get all appointments by doctor
    List<AppointmentResponseDto> getAppointmentsByDoctor(int doctorId);

    //get all appointment by patient
    List<AppointmentResponseDto> getAppointmentsByPatient(int patientId);

    //cancel
    void cancelAppointment(int appointmentId);

}
