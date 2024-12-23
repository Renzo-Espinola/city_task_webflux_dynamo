package com.hiperyum.city.task.api.domain.ports.in;

import org.springframework.scheduling.config.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Mono<Task> saveTask(Task task);
    Mono<Task> getTaskById(Long id);
    Flux<Task> getAllTasks();
    Mono<Task> updateTasks(Task task);
    Mono<Void> deleteTasks(Task task);
}
