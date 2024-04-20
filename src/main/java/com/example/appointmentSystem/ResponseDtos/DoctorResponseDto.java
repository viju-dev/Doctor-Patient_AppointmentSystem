package com.example.appointmentSystem.ResponseDtos;

import com.example.appointmentSystem.Enums.genderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorResponseDto {
    private Integer id;
    private String name;
    private String mobNo;
    @Enumerated(EnumType.STRING)
    private genderEnum gender;
    private String fees;
    private String description;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;
    private Integer slotTime ; //in mins
}
