package com.hiperyum.city.task.api.infraestructure.adapter.out.persistence;

import com.hiperyum.city.task.api.domain.ports.out.TaskCrudService;
import com.hiperyum.city.task.api.infraestructure.entity.TaskEntity;
import com.hiperyum.city.task.api.infraestructure.mapper.TaskMapper;
import com.hiperyum.city.task.api.infraestructure.repository.TaskEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.hiperyum.city.task.api.domain.model.Task;
import reactor.core.scheduler.Scheduler;

import java.time.ZonedDateTime;

@Component
public class TaskPersistenceAdapter implements TaskCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskPersistenceAdapter.class);
    private TaskEntityRepository taskEntityRepository;
    private TaskMapper taskMapper;
    private Scheduler scheduler;

    public TaskPersistenceAdapter(TaskEntityRepository taskEntityRepository, TaskMapper taskMapper, Scheduler scheduler) {
        this.taskEntityRepository = taskEntityRepository;
        this.taskMapper = taskMapper;
        this.scheduler = scheduler;
    }

    @Override
    public Mono<Task> create(Task task) {
        return Mono
                .just(task)
                .map(taskMapper::modelToEntity)
                .flatMap(taskEntityRepository::save)
                .map(taskMapper::entityToModel);
    }

    @Override
    public Mono<Task> findById(Long id) {
        return taskEntityRepository
                .findById(id)
                .map(taskMapper::entityToModel)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<Task> findAll() {
        return taskEntityRepository
                .findAll()
                .map(taskMapper::entityToModel)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<Task> update(Long id, Task task) {
        return taskEntityRepository.findById(id)
                .flatMap(taskFound->{
                    TaskEntity newTask = new TaskEntity();
                    newTask.setId(task.getId());
                    newTask.setDescription(task.getDescription());
                    newTask.setDeviceAction(task.getDeviceAction());
                    newTask.setCreatedAt(task.getCreatedAt());
                    newTask.setHour(task.getHour());
                    newTask.setUpdatedAt(task.getUpdatedAt());
                    newTask.setDeviceId(task.getDeviceId());
                    newTask.setExecuteUntil(task.getExecuteUntil());
                    newTask.setName(task.getName());
                    newTask.setMinute(task.getMinute());
                    newTask.setExecutionDays(task.getExecutionDays());
                    newTask.setJobId(task.getJobId());
                    newTask.setExecutionCommand(task.getExecutionCommand());
                    return taskEntityRepository.save(newTask).map(taskMapper::entityToModel);
                }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> delete(Long id) {
       return taskEntityRepository.findById(id)
               .flatMap(taskFound-> taskEntityRepository.deleteById(id))
               .switchIfEmpty(Mono.empty());
    }
}
