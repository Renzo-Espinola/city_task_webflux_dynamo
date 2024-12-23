package com.hiperyum.city.task.api.infraestructure.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "Devices")
public class DeviceEntity {
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    private String name;
    private String description;
    private String status;
}
