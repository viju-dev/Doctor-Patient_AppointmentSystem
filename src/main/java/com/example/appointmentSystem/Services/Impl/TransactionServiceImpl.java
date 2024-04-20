package com.example.appointmentSystem.Services.Impl;

import com.example.appointmentSystem.Entities.Transaction;
import com.example.appointmentSystem.Exception.ResourceNotFoundException;
import com.example.appointmentSystem.Repositories.TransactionRepo;
import com.example.appointmentSystem.ResponseDtos.TransactionResponseDto;
import com.example.appointmentSystem.Services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TransactionResponseDto getTransaction(int transactionId) {
        Transaction transaction = transactionRepo.findById(transactionId).orElseThrow(()-> new ResourceNotFoundException("Transaction","id",String.valueOf(transactionId)));
        return this.modelMapper.map(transaction,TransactionResponseDto.class);
    }

    @Override
    public List<TransactionResponseDto> getAllTransactionsByPatient( int patientId) {
        List<TransactionResponseDto> transactions = transactionRepo.findTransactionsByPatientId(patientId).stream().map(transaction -> this.modelMapper.map(transaction,TransactionResponseDto.class)).collect(Collectors.toList());
        return transactions;
    }

    @Override
    public List<TransactionResponseDto> getAllTransactionsByAppointment(int appointmentId) {
        List<TransactionResponseDto> transactions = transactionRepo.findTransactionsByAppointmentId(appointmentId).stream().map(transaction -> this.modelMapper.map(transaction,TransactionResponseDto.class)).collect(Collectors.toList());;
        return transactions;
    }
}
