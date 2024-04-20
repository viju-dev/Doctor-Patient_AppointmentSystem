package com.example.appointmentSystem.Controllers;

import com.example.appointmentSystem.Entities.Doctor;
import com.example.appointmentSystem.EntryDtos.DoctorEntryDto;
import com.example.appointmentSystem.Enums.ActivationStatusEnum;
import com.example.appointmentSystem.Models.ApiResponse;
import com.example.appointmentSystem.ResponseDtos.DoctorResponseDto;
import com.example.appointmentSystem.Services.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.Socket;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Operation(summary = "Create Doctor",description = "Creates a new doctor also pass arrival and departure time in this format e.g.-> 06:00:00 ")
    @PostMapping("/")
    ResponseEntity createDoctor(@RequestBody DoctorEntryDto doctor){
        DoctorResponseDto savedDoctor = doctorService.createDoctor(doctor);
        return new ResponseEntity<>(new ApiResponse<>("Doctor created Successfully..!",true,savedDoctor), HttpStatus.CREATED);
    };

    @Operation(summary = "Update Doctor",description = "Updates an existing doctor with new details by doctorId")
    @PutMapping("/{doctorId}")
    ResponseEntity updateDoctor(@RequestBody DoctorEntryDto doctor,@PathVariable int doctorId){
        DoctorResponseDto savedDoctor = doctorService.updateDoctor(doctor,doctorId);
        return new ResponseEntity<>(new ApiResponse<>("Doctor updated Successfully..!",true,savedDoctor), HttpStatus.OK);
    };

    @Operation(summary = "Update Doctor's ActivationStatus", description = "Update Doctor's Account ActivationStatus in terms of ReActivating Account")
    @PatchMapping("/{doctorId}")
    ResponseEntity updateStatus(@PathVariable int doctorId,@RequestParam ActivationStatusEnum status){
        doctorService.updateDoctorActivation(doctorId,status);
        return new ResponseEntity<>(new ApiResponse<>("Doctor Activation status updated Successfully..!",true), HttpStatus.OK);
    };

    @Operation(summary = "Get doctor", description = "Returns a Specific Doctor Information with the specified id")
    @GetMapping("/{doctorId}/")
    ResponseEntity getDoctor(@PathVariable int doctorId){
        DoctorResponseDto savedDoctor = doctorService.getDoctor(doctorId);
        return new ResponseEntity<>(new ApiResponse<>("Doctor information fetched Successfully..!",true,savedDoctor), HttpStatus.OK);
    };

    @Operation(summary = "Get All Doctors",description = "Get all Doctors information")
    @GetMapping("/")
    public ResponseEntity getAll(){
        List<DoctorResponseDto> doctors= doctorService.getAll();
        return new ResponseEntity<>(new ApiResponse<>("All Doctor's Information retrieved Successfully..!",true,doctors), HttpStatus.OK);//new ApiResponse<>(true,doctors)
    };

    @Operation(summary = "Doctor Availability", description = "Returns Information about Specific Doctor's Availability by passing doctorId")
    @GetMapping("/available/{doctorId}/")
    public ResponseEntity isDoctorAvailable(@PathVariable int doctorId){
        Boolean available = doctorService.isDoctorAvailable(doctorId);
        if (available){
            return new ResponseEntity<>(new ApiResponse<>("Doctor is available..!",true), HttpStatus.OK);//new ApiResponse<>(true,doctors)
        }
        return new ResponseEntity<>(new ApiResponse<>("Doctor is not available..!",true), HttpStatus.OK);//new ApiResponse<>(true,doctors)
    };

    @Operation(summary = "Get All Available Doctors",description = "Returns All Available Doctors Information")
    @GetMapping("/available/")
    public ResponseEntity getAllAvailableDoctors(){
        List<DoctorResponseDto> doctors= doctorService.getAllAvailableDoctors();
        if(doctors.size() == 0 || doctors.equals(null)){
            return new ResponseEntity<>(new ApiResponse<>("Doctors are not available for today..!",true), HttpStatus.OK);//new ApiResponse<>(true,doctors)
        }
        return new ResponseEntity<>(new ApiResponse<>("All Available Doctor's Information retrieved Successfully..!",true,doctors), HttpStatus.OK);//new ApiResponse<>(true,doctors)
    };

    @Operation(summary = "delete Doctor", description = "Changes Doctors status to deleted but don't delete doctor information from Database to maintain his appointment History")
    @DeleteMapping("/{doctorId}")
    ResponseEntity deleteDoctor(@PathVariable int doctorId){
        doctorService.deleteDoctor(doctorId);
        return new ResponseEntity<>(new ApiResponse<>("doctor with id : "+doctorId+" deleted successfully",true), HttpStatus.OK);
    };


}
