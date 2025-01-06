package com.hiperyum.city.task.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
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
}
