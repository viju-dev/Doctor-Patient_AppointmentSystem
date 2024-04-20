package com.example.appointmentSystem.Services.Impl;

import com.example.appointmentSystem.Entities.Appointment;
import com.example.appointmentSystem.Entities.Doctor;
import com.example.appointmentSystem.EntryDtos.DoctorEntryDto;
import com.example.appointmentSystem.Enums.ActivationStatusEnum;
import com.example.appointmentSystem.Exception.GlobalCustomException;
import com.example.appointmentSystem.Exception.ResourceNotFoundException;
import com.example.appointmentSystem.Repositories.AppointmentRepo;
import com.example.appointmentSystem.Repositories.DoctorRepo;
import com.example.appointmentSystem.ResponseDtos.DoctorResponseDto;
import com.example.appointmentSystem.Services.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppointmentRepo appointmentRepo;
    @Override
    public DoctorResponseDto createDoctor(DoctorEntryDto doctorDto) {
        System.out.println(doctorDto);
        Doctor doctor = this.modelMapper.map(doctorDto,Doctor.class);
        doctor.setStatus(ActivationStatusEnum.ACTIVATE);
        Doctor savedDoctor = doctorRepo.save(doctor);
        return this.modelMapper.map(savedDoctor,DoctorResponseDto.class);
    }


    @Override
    public DoctorResponseDto updateDoctor(DoctorEntryDto doctorDto, int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("doctor","id",String.valueOf(doctorId)));
        if (doctor.getStatus().toString().equalsIgnoreCase("DEACTIVATE")){
            throw  new GlobalCustomException("Doctor with given id : "+doctorId+" deleted, ReActivate account to update");
        }
        doctor.setName(doctorDto.getName());
        doctor.setDescription(doctorDto.getDescription());
        doctor.setFees(doctorDto.getFees());
        doctor.setArrivalTime(doctorDto.getArrivalTime());
        doctor.setDepartureTime(doctorDto.getDepartureTime());
        doctor.setGender(doctorDto.getGender());
        doctor.setMobNo(doctorDto.getMobNo());
        doctor.setSlotTime(doctorDto.getSlotTime());
        Doctor savedDoctor = doctorRepo.save(doctor);
        return this.modelMapper.map(savedDoctor,DoctorResponseDto.class);
    }

    @Override
    public DoctorResponseDto updateDoctorActivation(int doctorId, ActivationStatusEnum status) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("doctor","id",String.valueOf(doctorId)));
        doctor.setStatus(status);
        doctorRepo.save(doctor);
        return this.modelMapper.map(doctor,DoctorResponseDto.class);
    }

    @Override
    public DoctorResponseDto getDoctor(int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("doctor","id",String.valueOf(doctorId)));
        return this.modelMapper.map(doctor,DoctorResponseDto.class);
    }

    @Override
    public List<DoctorResponseDto> getAll() {
        List<DoctorResponseDto> doctors = doctorRepo.findAll().stream().map(doctor -> this.modelMapper.map(doctor,DoctorResponseDto.class)).collect(Collectors.toList());
        return doctors;
    }

    @Override
    public Boolean isDoctorAvailable(int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("doctor","id",String.valueOf(doctorId)));
        if(appointmentRepo.findAppointmentsByDoctorIdAndDate(doctorId, LocalDate.now()).size() > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<DoctorResponseDto> getAllAvailableDoctors() {
        List<DoctorResponseDto> doctors = doctorRepo.findAll().stream().filter(doctor -> appointmentRepo.findAppointmentsByDoctorIdAndDate(doctor.getId(),LocalDate.now()).size() > 0).map(doctor -> this.modelMapper.map(doctor,DoctorResponseDto.class)).collect(Collectors.toList());
        return doctors;
    }

    @Override
    public void deleteDoctor(int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("doctor","id",String.valueOf(doctorId)));
        doctor.setStatus(ActivationStatusEnum.DEACTIVATE); // just changing account status to be DEACTIVATE as we want to maintain history
    }
}
