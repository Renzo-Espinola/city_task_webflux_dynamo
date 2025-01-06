package com.hiperyum.city.task.api.infraestructure.adapter.in.handler.device;

import com.amazonaws.util.StringUtils;
import com.hiperyum.city.task.api.ports.in.device.CreateDeviceUseCase;
import com.hiperyum.city.task.api.ports.in.device.FindDeviceByIdUseCase;
import com.hiperyum.city.task.api.ports.in.device.UpdateDeviceUseCase;
import com.hiperyum.city.task.api.domain.model.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeviceHandler {
    private final CreateDeviceUseCase createDeviceUseCase;
    private final FindDeviceByIdUseCase findDeviceByIdUseCase;
    private final UpdateDeviceUseCase updateDeviceUseCase;

    public Mono<ServerResponse> getDeviceById(ServerRequest request){
        String deviceId = request.pathVariable("id");
        return errorNotFoundHandler(findDeviceByIdUseCase
                .execute(deviceId)
                .flatMap(device -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(device)
                        .switchIfEmpty(
                                ServerResponse
                                        .notFound()
                                        .build()
                        )));
    }

    public Mono<ServerResponse> createDevice(ServerRequest request){
        Mono<Device> device = request.bodyToMono(Device.class);
        return device
                .flatMap(createDeviceUseCase::execute)
                .flatMap(dev -> {
                    if(StringUtils.isNullOrEmpty(dev.getId())){
                        return ServerResponse.badRequest().build();
                    }
                    return errorBadRequestHandler(ServerResponse.created(URI.create("/api/device/".concat(dev.getId())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(dev));
                });
    }


    private Mono<ServerResponse> errorNotFoundHandler (Mono<ServerResponse> response){
        return response.onErrorResume(error -> {
            WebClientResponseException errorResponse = (WebClientResponseException) error;
            if(HttpStatus.NOT_FOUND.equals(errorResponse.getStatusCode())){
                Map<String,Object> mapError = new HashMap<>();
                mapError.put("error","Not Found");
                mapError.put("timestamp", new Date());
                mapError.put("status", errorResponse.getStatusCode().value());
                return ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .bodyValue(mapError);
            }
            return Mono.error(errorResponse);
        });
    }

    private Mono<ServerResponse> errorBadRequestHandler (Mono<ServerResponse> response){
        return response.onErrorResume(error -> {
            WebClientResponseException errorResponse = (WebClientResponseException) error;
            if(HttpStatus.BAD_REQUEST.equals(errorResponse.getStatusCode())){
                Map<String,Object> mapError = new HashMap<>();
                mapError.put("error","Bad Request");
                mapError.put("timestamp", new Date());
                mapError.put("status", errorResponse.getStatusCode().value());
                return ServerResponse
                        .status(HttpStatus.BAD_REQUEST)
                        .bodyValue(mapError);
            }
            return Mono.error(errorResponse);
        });
    }

}
