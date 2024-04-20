package com.example.appointmentSystem.Services;

import com.example.appointmentSystem.Entities.Transaction;

import com.example.appointmentSystem.ResponseDtos.TransactionResponseDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {
    TransactionResponseDto getTransaction(@PathVariable int transactionId);

    List<TransactionResponseDto> getAllTransactionsByPatient( @PathVariable int patientId);

    List<TransactionResponseDto>getAllTransactionsByAppointment(@PathVariable int appointmentId);
}
