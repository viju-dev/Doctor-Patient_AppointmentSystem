package com.example.appointmentSystem.Entities;

import com.example.appointmentSystem.Enums.ActivationStatusEnum;
import com.example.appointmentSystem.Enums.genderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(nullable = false, unique = true)
    private String mobNo;
    @Enumerated(EnumType.STRING)
    private genderEnum gender;
    private Float fees;
    private String description;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;
    private Integer slotTime; //in mins usual 15 mins
    @Enumerated(EnumType.STRING)
    private ActivationStatusEnum status;

    @OneToMany(mappedBy = "doctor", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}) //cascade = CascadeType.ALL we don't want deleting doctor may delete all previous appointments data
    private List<Appointment> appointments = new ArrayList<>();


}
