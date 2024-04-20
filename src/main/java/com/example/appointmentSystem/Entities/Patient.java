package com.example.appointmentSystem.Entities;

import com.example.appointmentSystem.Enums.genderEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(nullable = false,unique = true)
    private String mobNo;
    private int age;
    @Enumerated(EnumType.STRING)
    private genderEnum gender;

    @OneToMany(mappedBy = "patient",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}) // we don't want to cascade type all
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH}) // we don't want to cascade type all
    private List<Transaction> Transactions = new ArrayList<>();
}
