package com.hiperyum.city.task.api.application.usecase.task;

import com.hiperyum.city.task.api.ports.in.task.UpdateTaskUseCase;
import com.hiperyum.city.task.api.domain.model.Task;
import com.hiperyum.city.task.api.ports.out.TaskCrudService;
import com.hiperyum.city.task.api.infraestructure.exception.CustomException;
import com.hiperyum.city.task.api.infraestructure.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class UpdateTaskUseCaseImpl implements UpdateTaskUseCase {

    private final TaskCrudService taskCrudService;
    @Autowired
    public UpdateTaskUseCaseImpl(TaskCrudService taskCrudService) {
        this.taskCrudService = taskCrudService;
    }

    @Override
    public Mono<Task> execute(int id, Task task) {
        if(task==null || task.getId() < 0){
            return Mono.error(new IllegalArgumentException("Task cannot be null, or Id is wrong"));
        }
        return taskCrudService.findById(id)
                .flatMap(existingTask -> taskCrudService.update(id, existingTask))
                .onErrorMap(TaskNotFoundException.class, ex ->
                        new CustomException("An error ocurred", ex));
    }

}
