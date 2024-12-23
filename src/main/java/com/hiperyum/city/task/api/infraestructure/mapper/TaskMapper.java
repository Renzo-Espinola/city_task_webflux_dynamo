package com.hiperyum.city.task.api.infraestructure.mapper;

import com.hiperyum.city.task.api.infraestructure.entity.TaskEntity;
import org.mapstruct.Mapper;
import com.hiperyum.city.task.api.domain.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task entityToModel(TaskEntity entityTask);
    TaskEntity modelToEntity(Task modelTask);
}
