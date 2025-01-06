package com.hiperyum.city.task.api.application.usecase.device;

import com.hiperyum.city.task.api.ports.in.device.FindDeviceByIdUseCase;
import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.ports.out.DeviceCrudService;
import com.hiperyum.city.task.api.infraestructure.exception.CustomException;
import com.hiperyum.city.task.api.infraestructure.exception.DeviceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class FindDeviceUseCaseImpl implements FindDeviceByIdUseCase {
    private final DeviceCrudService deviceCrudService;

    public FindDeviceUseCaseImpl(DeviceCrudService deviceCrudService) {
        this.deviceCrudService = deviceCrudService;
    }

    @Override
    public Mono<Device> execute(String id) {
        return deviceCrudService
                .findById(id)
                .onErrorMap(DeviceNotFoundException.class, ex ->
                        new CustomException("Id not Found", ex));
    }
}
