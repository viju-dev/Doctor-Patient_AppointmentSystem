package com.example.appointmentSystem.Services;

import com.example.appointmentSystem.Entities.Patient;
import com.example.appointmentSystem.EntryDtos.PatientEntryDto;
import com.example.appointmentSystem.ResponseDtos.PatientResponseDto;

public interface PatientService {
    //create
    PatientResponseDto createPatient(PatientEntryDto patient);

    //update
    PatientResponseDto updatePatient(PatientEntryDto patient,int patientId);

    //get
    PatientResponseDto getPatient(int patientId);

    //delete
    void deletePatient(int patientId);
}
