package com.example.appointmentSystem.Controllers;

import com.example.appointmentSystem.Entities.Patient;
import com.example.appointmentSystem.EntryDtos.PatientEntryDto;
import com.example.appointmentSystem.Models.ApiResponse;
import com.example.appointmentSystem.ResponseDtos.PatientResponseDto;
import com.example.appointmentSystem.Services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Operation(summary = "Create Patient",description = "Create a new patient")
    @PostMapping("/")
    ResponseEntity createPatient(@RequestBody PatientEntryDto patient){
        PatientResponseDto savedPatient = patientService.createPatient(patient);
        return  new ResponseEntity<>(new ApiResponse<>("Patient created Successfully..!",true, savedPatient), HttpStatus.CREATED);
    };

    @Operation(summary = "Update patient",description = "Update existing patient with new details by his patientId")
    @PutMapping("/{patientId}")
    ResponseEntity updatePatient(@RequestBody PatientEntryDto patient,@PathVariable int patientId){
        PatientResponseDto savedPatient = patientService.updatePatient(patient,patientId);
        return new ResponseEntity<>(new ApiResponse<>("Patient updated Successfully..!",true, savedPatient), HttpStatus.OK);

    };

    @Operation(summary = "Get Patient",description = "Gets patient Information by patientId")
    @GetMapping("/{patientId}")
    ResponseEntity getPatient(@PathVariable int patientId){
        PatientResponseDto savedPatient = patientService.getPatient(patientId);
        return new ResponseEntity<>(new ApiResponse<>("Patient's data retrieved Successfully..!",true, savedPatient), HttpStatus.OK);
    };

    @Operation(summary = "Delete Patient", description = "Deletes a existing patient by patientId")
    @DeleteMapping("/{patientId}")
    ResponseEntity deletePatient(@PathVariable int patientId){
        patientService.deletePatient(patientId);
        return new ResponseEntity<>(new ApiResponse<>( "patient with id : "+patientId+" deleted successfully",true), HttpStatus.OK);
    };
}
