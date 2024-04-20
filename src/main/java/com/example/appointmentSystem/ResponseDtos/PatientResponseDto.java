package com.example.appointmentSystem.ResponseDtos;

import com.example.appointmentSystem.Enums.genderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientResponseDto {
    private Integer id;
    private String name;
    private int age;
    private String mobNo;
    @Enumerated(EnumType.STRING)
    private genderEnum gender;
}
