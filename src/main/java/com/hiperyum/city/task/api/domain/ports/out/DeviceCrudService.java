package com.hiperyum.city.task.api.domain.ports.out;

import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.infraestructure.entity.DeviceEntity;
import reactor.core.publisher.*;

public interface DeviceCrudService {
    Mono<Device> findById(String id);
    Mono<Device> save(Device device);
    Mono<Void> delete(String id);
    Mono<Void> update(String id, Device device);
}
