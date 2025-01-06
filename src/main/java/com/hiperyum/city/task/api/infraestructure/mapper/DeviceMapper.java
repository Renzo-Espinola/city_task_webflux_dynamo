package com.hiperyum.city.task.api.infraestructure.mapper;

import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.infraestructure.entity.DeviceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    Device entityToModel(DeviceEntity modelDevice);
    DeviceEntity modelToEntity(Device entityDevice);
}
