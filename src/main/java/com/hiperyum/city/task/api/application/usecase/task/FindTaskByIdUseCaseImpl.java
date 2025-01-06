package com.hiperyum.city.task.api.application.usecase.task;

import com.hiperyum.city.task.api.ports.in.task.FindTaskByIdUseCase;
import com.hiperyum.city.task.api.domain.model.Task;
import com.hiperyum.city.task.api.ports.out.TaskCrudService;
import com.hiperyum.city.task.api.infraestructure.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class FindTaskByIdUseCaseImpl implements FindTaskByIdUseCase {
    private final TaskCrudService taskCrudService;
    @Autowired
    public FindTaskByIdUseCaseImpl(TaskCrudService taskCrudService) {
        this.taskCrudService = taskCrudService;
    }
    @Override
    public Mono<Task> execute(int id) {
        if(id < 0){
            return Mono.error(new IllegalArgumentException("Id is wrong"));
        }
        return taskCrudService
                .findById(id)
                .switchIfEmpty(Mono.error(new CustomException("ID not Found!")));
    }
}
