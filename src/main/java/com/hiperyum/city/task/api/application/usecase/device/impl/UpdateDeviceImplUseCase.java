package com.hiperyum.city.task.api.application.usecase.device.impl;

import com.hiperyum.city.task.api.application.usecase.device.UpdateDeviceUseCase;
import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.domain.ports.out.DeviceCrudService;
import com.hiperyum.city.task.api.infraestructure.exception.CustomException;
import com.hiperyum.city.task.api.infraestructure.exception.DeviceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class UpdateDeviceImplUseCase implements UpdateDeviceUseCase {
    private final DeviceCrudService deviceCrudService;

    public UpdateDeviceImplUseCase(DeviceCrudService deviceCrudService) {
        this.deviceCrudService = deviceCrudService;
    }

    @Override
    public Mono<Void> execute(String id, Device updatedDevice) {
        // Aquí se puede incluir lógica adicional, como validaciones o eventos
        if (updatedDevice.getName() == null || updatedDevice.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("Device name cannot be empty"));
        }

        return deviceCrudService.findById(id)
                .flatMap(existingDevice -> deviceCrudService.update(id, updatedDevice))
                .onErrorMap(DeviceNotFoundException.class, ex ->
                        new CustomException("An error ocurred", ex));
    }
}
