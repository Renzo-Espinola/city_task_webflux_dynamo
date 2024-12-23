package com.hiperyum.city.task.api.infraestructure.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
