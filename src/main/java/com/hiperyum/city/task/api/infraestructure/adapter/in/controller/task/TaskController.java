package com.hiperyum.city.task.api.infraestructure.adapter.in.controller.task;

import com.hiperyum.city.task.api.application.usecase.task.UpdateTaskUseCase;
import com.hiperyum.city.task.api.domain.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    public static final String TASK_NOT_FOUND_WITH_ID = "task not found with ID: ";
    @Autowired
    private UpdateTaskUseCase updateTaskUseCase;

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Task>> updateTask(@PathVariable Long id, @RequestBody Task task){
        return updateTaskUseCase.execute(id, task).map(taskUpdated -> {
            return ResponseEntity
                    .created(URI.create("/api/tasks/".concat(String.valueOf(taskUpdated.getId()))))
                    .contentType(MediaType.APPLICATION_JSON).body(task);
        }).onErrorResume(err->Mono.just(ResponseEntity.noContent().build()));
    }

}
