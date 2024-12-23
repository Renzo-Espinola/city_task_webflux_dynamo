package com.hiperyum.city.task.api.infraestructure.repository;

import com.hiperyum.city.task.api.domain.model.Task;
import com.hiperyum.city.task.api.infraestructure.entity.TaskEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TaskEntityRepository extends R2dbcRepository<TaskEntity, Long> {
    Task findByJobId(String jobId);
}
