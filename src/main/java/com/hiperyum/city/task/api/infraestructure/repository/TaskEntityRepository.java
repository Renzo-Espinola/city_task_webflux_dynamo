package com.hiperyum.city.task.api.infraestructure.repository;

import com.hiperyum.city.task.api.infraestructure.entity.TaskEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
@Repository
public interface TaskEntityRepository extends ReactiveCrudRepository<TaskEntity, Integer> {
    Mono<TaskEntity> findByJobId(String jobId);
}
