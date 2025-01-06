package com.hiperyum.city.task.api.application.usecase.task;

import com.hiperyum.city.task.api.ports.in.task.CreateTaskUseCase;
import com.hiperyum.city.task.api.domain.model.Task;
import com.hiperyum.city.task.api.ports.out.TaskCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class CreateTaskUseCaseImpl implements CreateTaskUseCase {
    private final TaskCrudService taskCrudService;

    @Autowired
    public CreateTaskUseCaseImpl(TaskCrudService taskCrudService) {
        this.taskCrudService = taskCrudService;
    }

    @Override
    public Mono<Task> execute(Task task) {
       return taskCrudService.create(task);
    }
}
