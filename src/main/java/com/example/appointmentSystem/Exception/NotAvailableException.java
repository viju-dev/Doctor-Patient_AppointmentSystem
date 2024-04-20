package com.example.appointmentSystem.Exception;

public class NotAvailableException extends RuntimeException{
    String resourceName;
    String fieldName;
    String fieldValue;

    public NotAvailableException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with id %s : %s is Not Available / Already Booked...Try Another !",resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
