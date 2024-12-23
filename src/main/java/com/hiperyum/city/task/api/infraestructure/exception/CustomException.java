package com.hiperyum.city.task.api.infraestructure.exception;

public class CustomException extends RuntimeException{
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
