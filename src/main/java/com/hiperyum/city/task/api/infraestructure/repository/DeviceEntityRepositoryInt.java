package com.hiperyum.city.task.api.infraestructure.repository;

import com.hiperyum.city.task.api.domain.model.Device;
import com.hiperyum.city.task.api.infraestructure.entity.DeviceEntity;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.model.*;

public interface DeviceEntityRepositoryInt {
  Mono<GetItemResponse> findById(String id);
  Mono<PutItemResponse> save(DeviceEntity deviceEntity);
  Mono<Void> delete(String id);
  Mono<Void> update(String id, Device deviceUpdated);
}
