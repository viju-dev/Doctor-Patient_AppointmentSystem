package com.example.appointmentSystem.Repositories;

import com.example.appointmentSystem.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {
    List<Transaction> findTransactionsByPatientId(int patientId);

    List<Transaction> findTransactionsByAppointmentId(int appointmentId);
}
