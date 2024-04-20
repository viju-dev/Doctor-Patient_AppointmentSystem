package com.example.appointmentSystem.EntryDtos;

import com.example.appointmentSystem.Entities.Appointment;
import com.example.appointmentSystem.Enums.ActivationStatusEnum;
import com.example.appointmentSystem.Enums.genderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class DoctorEntryDto {

    @NotEmpty(message = "Name can't be empty")
    private String name;
    @NotEmpty(message = "mobile no. can't be empty")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobNo;
    @NotEmpty(message = "gender can't be empty")
    @Enumerated(EnumType.STRING)
    private genderEnum gender;
    @Positive(message = "fees must be greater than 0")
    private Float fees;
    private String description;
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "Arrival time can't be null")
    private LocalTime arrivalTime;
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @NotNull(message = "Departure time can't be null")
    private LocalTime departureTime;
    @Min(value = 10,message = "minimum slot time must be 10 ")
    private Integer slotTime ; //in mins


}
