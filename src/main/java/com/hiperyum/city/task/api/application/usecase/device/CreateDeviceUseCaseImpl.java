package com.hiperyum.city.task.api.application.usecase.device;

import ch.qos.logback.core.util.StringUtil;
import com.hiperyum.city.task.api.ports.in.device.CreateDeviceUseCase;
import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.ports.out.DeviceCrudService;
import com.hiperyum.city.task.api.infraestructure.exception.CustomException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateDeviceUseCaseImpl implements CreateDeviceUseCase {

    private final DeviceCrudService deviceCrudService;

    public CreateDeviceUseCaseImpl(DeviceCrudService deviceCrudService) {
        this.deviceCrudService = deviceCrudService;
    }

    @Override
    public Mono<Device> execute(Device modelDevice) {
        if(modelDevice == null || StringUtil.isNullOrEmpty(modelDevice.getId())){
            return Mono.error(new IllegalArgumentException("Task cannot be null, or Id is wrong"));
        }
       return deviceCrudService
               .save(modelDevice)
               .flatMap(Mono::just)
               .onErrorMap(throwable ->
                       new CustomException(throwable.getMessage(), throwable));

    }
}
