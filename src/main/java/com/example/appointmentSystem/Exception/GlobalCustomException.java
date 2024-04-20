package com.example.appointmentSystem.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalCustomException extends RuntimeException{
    private String message;

}
