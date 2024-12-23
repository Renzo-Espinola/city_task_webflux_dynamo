package com.hiperyum.city.task.api.infraestructure.repository;

import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.infraestructure.entity.DeviceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DeviceEntityRepository implements DeviceEntityRepositoryInt {
    private static final String DB_NAME = "Devices";
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceEntityRepository.class);

    private final DynamoDbAsyncClient dynamoDbAsyncClient;

    public DeviceEntityRepository(DynamoDbAsyncClient dynamoDbAsyncClient) {
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
    }

    public Mono<GetItemResponse> findById(String id) {
        LOGGER.debug("findById(): {}", id);
        var key = Map.of("id", AttributeValue.builder().s(id).build());
        var request = GetItemRequest.builder()
                                    .tableName(DB_NAME)
                                    .key(key)
                                    .build();
        return Mono.fromFuture(dynamoDbAsyncClient.getItem(request));
    }

    public Mono<PutItemResponse> save(DeviceEntity deviceEntity){
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(deviceEntity.getId()).build());
        item.put("name", AttributeValue.builder().s(deviceEntity.getName()).build());
        item.put("description", AttributeValue.builder().s(deviceEntity.getDescription()).build());
        item.put("status", AttributeValue.builder().s(deviceEntity.getStatus()).build());

        PutItemRequest request = PutItemRequest
                                            .builder()
                                            .tableName(DB_NAME)
                                            .item(item)
                                            .build();

        return Mono.fromFuture(dynamoDbAsyncClient.putItem(request));
    }

    public Mono<Void> delete(String id){
        var key = Map.of("id", AttributeValue.builder().s(id).build());
        var request =  DeleteItemRequest.builder()
                                     .tableName(DB_NAME)
                                     .key(key).build();
        return Mono.fromFuture(dynamoDbAsyncClient.deleteItem(request)).then();
    }


    public Mono<Void> update(String id, Device deviceUpdated) {
        // Crear las claves primarias para buscar el dispositivo
        var key = Map.of("id", AttributeValue.builder().s(id).build());

        // Crear un mapa con los atributos a actualizar
        var updates = new HashMap<String, AttributeValueUpdate>();
        updates.put("name", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(deviceUpdated.getName()).build())
                .action(AttributeAction.PUT)
                .build());
        updates.put("description", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(deviceUpdated.getDescription()).build())
                .action(AttributeAction.PUT)
                .build());
        updates.put("status", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(deviceUpdated.getStatus()).build())
                .action(AttributeAction.PUT)
                .build());

        // Construir la solicitud de actualización
        var request = UpdateItemRequest.builder()
                .tableName(DB_NAME)
                .key(key)
                .attributeUpdates(updates)
                .build();

        // Ejecutar la actualización de forma reactiva
        return Mono.fromFuture(dynamoDbAsyncClient.updateItem(request)).then();
    }
}
