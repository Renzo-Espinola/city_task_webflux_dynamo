package com.hiperyum.city.task.api.ports.out;

import com.hiperyum.city.task.api.domain.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskCrudService {
    Mono<Task> create(Task task);
    Mono<Task> findById(int id);
    Flux<Task> findAll();
    Mono<Task> update (int id, Task task);
    Mono<Void> delete(int id);
}
