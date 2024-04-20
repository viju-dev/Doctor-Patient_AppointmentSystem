package com.example.appointmentSystem.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>{
    private String message;
    private Boolean success ;
    private T payload;

    public ApiResponse(String message,Boolean success) {
        this.message = message;
        this.success = success;
    }
}
