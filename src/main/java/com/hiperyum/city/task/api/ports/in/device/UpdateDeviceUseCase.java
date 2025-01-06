package com.hiperyum.city.task.api.ports.in.device;

import com.hiperyum.city.task.api.domain.model.Device;
import reactor.core.publisher.Mono;

public interface UpdateDeviceUseCase {
    Mono<Void> execute(String id, Device updatedDevice);
}
