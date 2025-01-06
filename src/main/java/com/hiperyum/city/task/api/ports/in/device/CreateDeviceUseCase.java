package com.hiperyum.city.task.api.ports.in.device;

import com.hiperyum.city.task.api.domain.model.Device;
import reactor.core.publisher.Mono;

public interface CreateDeviceUseCase {
   Mono<Device> execute(Device modelDevice);
}
