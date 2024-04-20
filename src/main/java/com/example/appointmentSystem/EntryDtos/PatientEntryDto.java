package com.example.appointmentSystem.EntryDtos;

import com.example.appointmentSystem.Enums.genderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PatientEntryDto {
    @NotEmpty(message = "Name can't be empty")
    private String name;
    @NotEmpty(message = "mobile no. can't be empty")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobNo;

    @Min(value = 1,message = "Age can't be less than 1")
    private int age;
    @NotEmpty(message = "Gender can't be empty")
    @Enumerated(EnumType.STRING)
    private genderEnum gender;
}
