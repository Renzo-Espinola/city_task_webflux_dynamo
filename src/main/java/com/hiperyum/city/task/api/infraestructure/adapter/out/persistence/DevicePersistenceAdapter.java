package com.hiperyum.city.task.api.infraestructure.adapter.out.persistence;

import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.ports.out.DeviceCrudService;
import com.hiperyum.city.task.api.infraestructure.exception.DeviceNotFoundException;
import com.hiperyum.city.task.api.infraestructure.mapper.DeviceMapper;
import com.hiperyum.city.task.api.infraestructure.repository.DeviceEntityRepository;
import com.hiperyum.city.task.api.infraestructure.repository.DeviceEntityRepositoryInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DevicePersistenceAdapter implements DeviceCrudService {
    @Autowired
    private final DeviceEntityRepositoryInt deviceEntityRepository;
    private final DeviceMapper deviceMapper;

    public DevicePersistenceAdapter(DeviceEntityRepository deviceEntityRepository, DeviceMapper deviceMapper) {
        this.deviceEntityRepository = deviceEntityRepository;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public Mono<Device> findById(String id) {
        return deviceEntityRepository.findById(id)
                .map(response -> {
                    var item = response.item();
                    return new Device(
                            item.get("id").s(),
                            item.get("name").s(),
                            item.get("description").s(),
                            item.get("status").s());
                }).onErrorMap(e -> new DeviceNotFoundException("Device not found with ID: " + id, e));
    }

    @Override
    public Mono<Device> save(Device modelDevice) {
        var entityDevice = deviceMapper.modelToEntity(modelDevice);
        return deviceEntityRepository
                .save(entityDevice)
                .map(response -> {
                    var itemResponse = response.attributes();
                    return new Device(
                            itemResponse.get("id").s(),
                            itemResponse.get("name").s(),
                            itemResponse.get("description").s(),
                            itemResponse.get("status").s());
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return deviceEntityRepository.findById(id)
                .flatMap(device -> deviceEntityRepository.delete(id))
                .switchIfEmpty(Mono.error(new DeviceNotFoundException("Device not found with ID: " + id)));
    }

    @Override
    public Mono<Void> update(String id, Device device) {
        return deviceEntityRepository
                .findById(id)
                .flatMap(found->deviceEntityRepository.update(id, device))
                .switchIfEmpty(Mono.error(new DeviceNotFoundException("Device not found with ID: " + id)));
    }

}
