package com.example.appointmentSystem.Services.Impl;

import com.example.appointmentSystem.Entities.Patient;
import com.example.appointmentSystem.EntryDtos.PatientEntryDto;
import com.example.appointmentSystem.Exception.ResourceNotFoundException;
import com.example.appointmentSystem.Repositories.PatientRepo;
import com.example.appointmentSystem.ResponseDtos.PatientResponseDto;
import com.example.appointmentSystem.Services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PatientResponseDto createPatient(PatientEntryDto patientDto) {
        Patient patient = this.modelMapper.map(patientDto,Patient.class);
        Patient savedPatient = patientRepo.save(patient);
        return this.modelMapper.map(savedPatient,PatientResponseDto.class);
    }

    @Override
    public PatientResponseDto updatePatient(PatientEntryDto patientDto, int patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(()-> new ResourceNotFoundException("patient","id",String.valueOf(patientId)));
        patient.setName(patientDto.getName());
        patient.setAge(patientDto.getAge());
        patient.setGender(patientDto.getGender());
        patient.setMobNo(patientDto.getMobNo());
        Patient savedPatient = patientRepo.save(patient);
        return this.modelMapper.map(savedPatient,PatientResponseDto.class);
    }

    @Override
    public PatientResponseDto getPatient(int patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(()->new ResourceNotFoundException("patient","id",String.valueOf(patientId)));
        return this.modelMapper.map(patient,PatientResponseDto.class);
    }

    @Override
    public void deletePatient(int patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(()-> new ResourceNotFoundException("patient","id",String.valueOf(patientId)));
        patientRepo.delete(patient);
    }
}
