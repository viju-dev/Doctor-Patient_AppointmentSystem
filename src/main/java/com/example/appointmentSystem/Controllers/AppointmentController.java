package com.example.appointmentSystem.Controllers;

import com.example.appointmentSystem.Exception.GlobalCustomException;
import com.example.appointmentSystem.Models.ApiResponse;
import com.example.appointmentSystem.ResponseDtos.AppointmentResponseDto;
import com.example.appointmentSystem.Services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Operation(summary = "Create Appointments Slots", description = "If doctor is available he'll call this api to set his availability and so the slots for that specific doctor get created for that day")
    @PostMapping("/{doctorId}")
    ResponseEntity createAppointmentSlots(@PathVariable int doctorId) {
//        if (LocalDate.now().getDayOfWeek().toString().equalsIgnoreCase("sunday")) {
//            throw new GlobalCustomException("Today is sunday have some rest doctor :) ");
//        }
        List<AppointmentResponseDto> appointments = this.appointmentService.createAppointmentSlots(doctorId);
        return new ResponseEntity<>(new ApiResponse<>(" Successfully created Appointments for today..!", true, appointments), HttpStatus.CREATED);
    }

    @Operation(summary = "Book Appointment", description = "If doctor have available slots then we can book his appointment from here by passing appointmentId and patientId")
    @PostMapping("/{appointmentId}/patients/{patientId}")
    ResponseEntity bookAppointement(@PathVariable int appointmentId, @PathVariable int patientId) {
        AppointmentResponseDto appointment = appointmentService.bookAppointement(appointmentId, patientId);
        return new ResponseEntity<>(new ApiResponse<>("Appointment booked Successfully..!", true, appointment), HttpStatus.OK);
    }

    @Operation(summary = "Get Appointment", description = "Returns appointment details by passing appointmentId")
    @GetMapping("/{appointmentId}")
    ResponseEntity getAppointment(@PathVariable int appointmentId) {
        AppointmentResponseDto appointment = appointmentService.getAppointment(appointmentId);
        return new ResponseEntity<>(new ApiResponse<>("Appointment data fetched successfully..!", true, appointment), HttpStatus.OK);
    }

    @Operation(summary = "Get Available Slots", description = "Returns a list of available slots for the specified doctor by passing doctorId.")
    @GetMapping("/doctors/{doctorId}/available/")
    ResponseEntity getAvailableSlotsByDoctor(@PathVariable int doctorId) {
        List<AppointmentResponseDto> appointments = appointmentService.getAvailableAppointmentsByDoctor(doctorId);
        if (appointments.size() == 0 || appointments.equals(null)) {
            return new ResponseEntity<>(new ApiResponse<>("Sorry ! No Slots Available for today try tomorrow..!", true, appointments), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse<>("Available slots information retrieved successfully..!", true, appointments), HttpStatus.OK);
    }

    @Operation(summary = "Get All Appointments by Doctor",description = "Returns all History of Doctor's appointments by passing doctorId")
    @GetMapping("/doctors/{doctorId}")
    ResponseEntity getAppointmentsByDoctor(@PathVariable int doctorId) {
        List<AppointmentResponseDto> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(new ApiResponse<>("Doctor's all Appointments data retrieved Successfully..!", true, appointments), HttpStatus.OK);
    }
    @Operation(summary = "Get All Appointments by Patient", description = "Returns all History of Patient's appointments by passing patientId")
    @GetMapping("/patients/{patientId}")
    ResponseEntity getAppointmentsByPatient(@PathVariable int patientId) {
        List<AppointmentResponseDto> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return new ResponseEntity<>(new ApiResponse<>("Patient's all Appointments data retrieved Successfully..!", true, appointments), HttpStatus.OK);
    }

    @Operation(summary = "Cancel Appointment", description = "It will cancel the appointment by passing appointmentId")
    @DeleteMapping("/{appointmentId}")
    ResponseEntity cancelAppointment(@PathVariable int appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return new ResponseEntity<>(new ApiResponse<>("Appointment with id : " + appointmentId + " cancelled successfully", true), HttpStatus.OK);
    }

}
