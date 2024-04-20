package com.example.appointmentSystem.Services.Impl;

import com.example.appointmentSystem.Entities.Appointment;
import com.example.appointmentSystem.Entities.Doctor;
import com.example.appointmentSystem.Entities.Patient;
import com.example.appointmentSystem.Entities.Transaction;
import com.example.appointmentSystem.Enums.TransactionStatusEnum;
import com.example.appointmentSystem.Exception.GlobalCustomException;
import com.example.appointmentSystem.Exception.NotAvailableException;
import com.example.appointmentSystem.Exception.ResourceNotFoundException;
import com.example.appointmentSystem.Repositories.AppointmentRepo;
import com.example.appointmentSystem.Repositories.DoctorRepo;
import com.example.appointmentSystem.Repositories.PatientRepo;
import com.example.appointmentSystem.Repositories.TransactionRepo;
import com.example.appointmentSystem.ResponseDtos.AppointmentResponseDto;
import com.example.appointmentSystem.Services.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private AppointmentRepo appointmentRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    TransactionRepo transactionRepo;


    //    @Scheduled(cron = "0 0 9 * * MON-SAT")  - by using scheduled annotation we can make slot creating process automated at specific time of everyday from monday to saturday
    @Override
    public List<AppointmentResponseDto> createAppointmentSlots(int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",String.valueOf(doctorId)));
        if (doctor.getStatus().toString().equalsIgnoreCase("DEACTIVATE")) {
            throw new GlobalCustomException("Doctor with given id " + doctorId + " is deleted Or Not Available, ReActivate Account to Create Slots");
        }
        List<Appointment> appointments = appointmentRepo.findAppointmentsByDoctorIdAndDate(doctorId, LocalDate.now());

        if (appointments.size() != 0) { // if already slots created then it will not create again and we'll return existing
            return doctor.getAppointments().stream().map(appointment -> this.modelMapper.map(appointment, AppointmentResponseDto.class)).collect(Collectors.toList());
        }

        int minutes = (int) doctor.getArrivalTime().until(doctor.getDepartureTime(), ChronoUnit.MINUTES);//getting total doctors available time in minutes
        int temp = 0;
        for (int i = 0; i < minutes / doctor.getSlotTime(); i++) {//creating slots based on doctor's available time and slot time
            Appointment appointment = new Appointment();
            LocalTime slot = doctor.getArrivalTime().plus(temp, ChronoUnit.MINUTES);
            temp += doctor.getSlotTime();
            appointment.setDate(LocalDate.now());
            appointment.setIsBooked(false);
            appointment.setAppointmentTime(slot);
            appointment.setDoctor(doctor);
            appointments.add(appointment);
        }
        doctor.setAppointments(appointments);
        Doctor savedDoctor = doctorRepo.save(doctor);
        List<AppointmentResponseDto> appointmentsList = savedDoctor.getAppointments().stream().map(appointment -> this.modelMapper.map(appointment, AppointmentResponseDto.class)).collect(Collectors.toList());

        return appointmentsList;
    }

    @Override
    public AppointmentResponseDto bookAppointement(int appointmentId, int patientId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException("appointment","id",String.valueOf(appointmentId)));
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",String.valueOf(patientId)));
        if(appointment.getIsBooked() && appointment.getPatient().equals(patient)){
            throw new GlobalCustomException("you have already booked this appointment");
        }
        else if (appointment.getIsBooked()) {
            throw new NotAvailableException("appointment", "id", String.valueOf(appointmentId));
        } else {
            appointment.setIsBooked(true);
            appointment.setBookedAt(LocalDateTime.now());
            appointment.setPatient(patient);
            appointment.setBookedAt(LocalDateTime.now());
        }
        Appointment savedAppointment = appointmentRepo.save(appointment);
        //making transaction
        Transaction transaction = new Transaction();
        transaction.setAppointment(appointment);
        transaction.setPatient(patient);
        transaction.setStatus(TransactionStatusEnum.BOOKING);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(transaction);

        return this.modelMapper.map(savedAppointment, AppointmentResponseDto.class);
    }

    @Override
    public AppointmentResponseDto getAppointment(int appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException("appointment","id",String.valueOf(appointmentId)));
        return this.modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Override
    public List<AppointmentResponseDto> getAvailableAppointmentsByDoctor(int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",String.valueOf(doctorId)));
        List<AppointmentResponseDto> appointments = appointmentRepo.findAppointmentsByDoctorIdAndDate(doctorId, LocalDate.now()).stream().filter(appointment -> !appointment.getIsBooked()).map(appointment -> this.modelMapper.map(appointment, AppointmentResponseDto.class)).collect(Collectors.toList());
        return appointments;
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByDoctor(int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new ResourceNotFoundException("doctor","id",String.valueOf(doctorId)));
        List<AppointmentResponseDto> appointments = doctor.getAppointments().stream().map(appointment1 -> this.modelMapper.map(appointment1, AppointmentResponseDto.class)).collect(Collectors.toList());
        return appointments;
    }

    @Override
    public List<AppointmentResponseDto> getAppointmentsByPatient(int patientId) {
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("patient","id",String.valueOf(patientId)));
        List<AppointmentResponseDto> appointments = patient.getAppointments().stream().map(appointment1 -> this.modelMapper.map(appointment1, AppointmentResponseDto.class)).collect(Collectors.toList());
        return appointments;
    }

    @Override
    public void cancelAppointment(int appointmentId) {
        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new ResourceNotFoundException("appointment","id",String.valueOf(appointmentId)));

        Transaction transaction = new Transaction();
        transaction.setAppointment(appointment);
        transaction.setPatient(appointment.getPatient());
        transaction.setStatus(TransactionStatusEnum.CANCELLATION);
        transaction.setTimestamp(LocalDateTime.now());

        appointment.setPatient(null);
        appointment.setIsBooked(false);
        appointment.setBookedAt(null);

        appointmentRepo.save(appointment);

        transactionRepo.save(transaction);

    }
}
