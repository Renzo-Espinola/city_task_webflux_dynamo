package com.hiperyum.city.task.api.application.usecase.device.impl;

import com.hiperyum.city.task.api.application.usecase.device.FindDeviceByIdUseCase;
import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.domain.ports.out.DeviceCrudService;
import com.hiperyum.city.task.api.infraestructure.exception.CustomException;
import com.hiperyum.city.task.api.infraestructure.exception.DeviceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class FindDeviceImplUseCase implements FindDeviceByIdUseCase {
    private final DeviceCrudService deviceCrudService;

    public FindDeviceImplUseCase(DeviceCrudService deviceCrudService) {
        this.deviceCrudService = deviceCrudService;
    }

    @Override
    public Mono<Device> execute(String id) {
        return deviceCrudService
                .findById(id)
                .onErrorMap(DeviceNotFoundException.class, ex ->
                        new CustomException("An error ocurred", ex));
    }
}
