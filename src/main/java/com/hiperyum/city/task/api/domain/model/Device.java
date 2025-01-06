package com.hiperyum.city.task.api.domain.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private String id;
    private String name;
    private String description;
    private String status;
}
