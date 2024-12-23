package com.hiperyum.city.task.api.domain.ports.in;

import com.hiperyum.city.task.api.domain.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeviceService {
    Mono<Task> getDeviceById(String id);
    Flux<Task> getAllDevices();
    Mono<Void> saveDevice(String id, Task updatedDevice);

}
