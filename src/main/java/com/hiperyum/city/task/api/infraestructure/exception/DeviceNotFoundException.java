package com.hiperyum.city.task.api.infraestructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    public DeviceNotFoundException(String message){
        super(message);
    }
}
