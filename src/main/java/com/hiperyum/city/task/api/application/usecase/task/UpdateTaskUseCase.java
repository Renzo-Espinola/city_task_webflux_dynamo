package com.hiperyum.city.task.api.application.usecase.task;

import com.hiperyum.city.task.api.domain.model.Task;
import reactor.core.publisher.Mono;

public interface UpdateTaskUseCase {
    Mono<Task> execute(Long id, Task task);
}
