package com.hiperyum.city.task.api.infraestructure.adapter.in.handler.task;

import com.hiperyum.city.task.api.ports.in.task.CreateTaskUseCase;
import com.hiperyum.city.task.api.ports.in.task.FindTaskByIdUseCase;
import com.hiperyum.city.task.api.ports.in.task.UpdateTaskUseCase;
import com.hiperyum.city.task.api.domain.model.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Component
public class TaskHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskHandler.class);
    public static final String TASK_NOT_FOUND_WITH_ID = "task not found with ID: ";
    @Autowired
    private UpdateTaskUseCase updateTaskUseCase;
    @Autowired
    private FindTaskByIdUseCase findTaskByIdUseCase;
    @Autowired
    private CreateTaskUseCase createTaskUseCase;

    @Operation(
            summary = "Update task",
            description = "Update a task with the provided task data",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public Mono<ServerResponse> updateTask(ServerRequest request) {
        Optional<Integer> taskId = Optional.of(Integer.valueOf(request.pathVariable("id")));
        Task task = request.bodyToMono(Task.class).block();
        return updateTaskUseCase.execute(taskId.get(), task)
                .map(taskUpdated ->
                        ServerResponse
                                .created(URI.create("/api/tasks/".concat(String.valueOf(taskUpdated.getId()))))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(taskUpdated))
                .onErrorResume(err -> Mono.just(ServerResponse.noContent().build())).block();
    }

    @Operation(
            summary = "Create task",
            description = "Create a new task with the provided data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public Mono<ServerResponse> createTask(ServerRequest request) {
        Task task = request.bodyToMono(Task.class).block();
        return createTaskUseCase.execute(task).flatMap(task1 ->
                ServerResponse
                        .created(URI.create("/api/tasks/".concat(String.valueOf(task1.getId()))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(task1)
        ).onErrorResume(err -> Mono.just(ServerResponse.noContent().build()).block());
    }

    @Operation(
            summary = "Find task by ID",
            description = "Retrieve a task by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "404", description = "Task not found")
            }
    )
    public Mono<ServerResponse> findTaskById(ServerRequest request) {
        Optional<Integer> taskId = Optional.of(Integer.valueOf(request.pathVariable("id")));
        return findTaskByIdUseCase.execute(taskId.get()).flatMap(task -> {
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(task)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

}
