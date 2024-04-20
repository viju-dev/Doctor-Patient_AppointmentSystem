package com.example.appointmentSystem.ResponseDtos;

import com.example.appointmentSystem.Enums.TransactionStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionResponseDto {
    private Integer id;
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private Integer patientId;
    private Integer appointmentId;

}
