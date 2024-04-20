package com.example.appointmentSystem.Controllers;

import com.example.appointmentSystem.Entities.Transaction;
import com.example.appointmentSystem.Models.ApiResponse;
import com.example.appointmentSystem.ResponseDtos.TransactionResponseDto;
import com.example.appointmentSystem.Services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Get Transaction",description = "Returns Transaction by passing transactionId")
    @GetMapping("/{transactionId}")
    ResponseEntity getTransaction(@PathVariable int transactionId){
        TransactionResponseDto transaction = transactionService.getTransaction(transactionId);
        return new ResponseEntity<>(new ApiResponse("Transaction data retrieved successfully",true,transaction), HttpStatus.OK);
    }

    @Operation(summary = "Get Transactions", description = "Returns Patient's all transactions by passing patientId")
    @GetMapping("/patients/{patientId}")
    ResponseEntity getAllTransactionsByPatient(@PathVariable int patientId){
        List<TransactionResponseDto> transactions = transactionService.getAllTransactionsByPatient(patientId);
        return new ResponseEntity<>(new ApiResponse("Patient's all transactions data retrieved successfully",true,transactions), HttpStatus.OK);
    }

    @Operation(summary = "Get Transactions", description = "Returns Appointment's all transactions by passing appointmentId")
    @GetMapping("/appointments/{appointmentId}")
    ResponseEntity getAllTransactionsByAppointment(@PathVariable int appointmentId){
        List<TransactionResponseDto> transactions = transactionService.getAllTransactionsByAppointment(appointmentId);
        return new ResponseEntity<>(new ApiResponse("Appointment's all transactions data retrieved successfully",true,transactions), HttpStatus.OK);
    }
}
