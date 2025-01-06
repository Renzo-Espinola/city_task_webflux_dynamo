package com.hiperyum.city.task.api.infraestructure.entity;

import io.r2dbc.spi.Row;
import org.springframework.data.relational.core.mapping.Table;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("task")
public class TaskEntity {
    @Id
    private int id;
    private String name;
    private String description;
    private String jobId;
    private int hour;
    private int minute;
    private String executionDays;
    private String executionCommand;
    private ZonedDateTime executeUntil;
    private String deviceId;
    private String deviceAction;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    // Método para mapear el resultado de la consulta R2DBC
    public static TaskEntity fromRow(Row row) {
        return TaskEntity.builder()
                .id(row.get("id", Integer.class))
                .name(row.get("name", String.class))
                .description(row.get("description", String.class))
                .jobId(row.get("job_id", String.class))
                .hour(row.get("task_hour", Integer.class))
                .minute(row.get("task_minute", Integer.class))
                .executionDays(row.get("execution_days", String.class))
                .executionCommand(row.get("execution_command", String.class))
                .executeUntil(row.get("execute_until", ZonedDateTime.class))
                .deviceId(row.get("device_id", String.class))
                .deviceAction(row.get("device_action", String.class))
                .createdAt(row.get("created_at", ZonedDateTime.class))
                .updatedAt(row.get("updated_at", ZonedDateTime.class))
                .build();
    }
}