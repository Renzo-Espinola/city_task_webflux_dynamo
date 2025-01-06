package com.hiperyum.city.task.api.ports.in.task;

import com.hiperyum.city.task.api.domain.model.Task;
import reactor.core.publisher.Mono;

public interface UpdateTaskUseCase {
    Mono<Task> execute(int id, Task task);
}
