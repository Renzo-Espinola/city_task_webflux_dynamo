package com.hiperyum.city.task.api.infraestructure.adapter.out.persistence;

import com.hiperyum.city.task.api.ports.out.TaskCrudService;
import com.hiperyum.city.task.api.infraestructure.entity.TaskEntity;
import com.hiperyum.city.task.api.infraestructure.mapper.TaskMapper;
import com.hiperyum.city.task.api.infraestructure.repository.TaskEntityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.hiperyum.city.task.api.domain.model.Task;

@Service("TaskPersistenceAdapter")
@RequiredArgsConstructor
public class TaskPersistenceAdapter implements TaskCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskPersistenceAdapter.class);
    private final TaskEntityRepository taskEntityRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final TaskMapper taskMapper;

    @Override
    public Mono<Task> create(Task task) {
        return Mono
                .just(task)
                .map(taskMapper::modelToEntity)
                .flatMap(taskEntityRepository::save)
                .map(taskMapper::entityToModel);
    }

    @Override
    public Mono<Task> findById(int id) {
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
    public Mono<Task> update(int id, Task task) {
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
    public Mono<Void> delete(int id) {
       return taskEntityRepository.findById(id)
               .flatMap(taskFound-> taskEntityRepository.deleteById(id))
               .switchIfEmpty(Mono.empty());
    }
}
