package com.example.appointmentSystem.Services;

import com.example.appointmentSystem.Entities.Doctor;
import com.example.appointmentSystem.EntryDtos.DoctorEntryDto;
import com.example.appointmentSystem.Enums.ActivationStatusEnum;
import com.example.appointmentSystem.ResponseDtos.DoctorResponseDto;

import java.util.List;

public interface DoctorService {
    //create
    DoctorResponseDto createDoctor(DoctorEntryDto doctor);

    //update
    DoctorResponseDto updateDoctor(DoctorEntryDto doctor,int doctorId);

    DoctorResponseDto updateDoctorActivation(int doctorId, ActivationStatusEnum status);

    //get
    DoctorResponseDto getDoctor(int doctorId);

    //get all
    List<DoctorResponseDto> getAll();

    Boolean isDoctorAvailable(int doctorId);

    List<DoctorResponseDto> getAllAvailableDoctors();
    //delete
    void deleteDoctor(int doctorId);

    //isAvailable



}
