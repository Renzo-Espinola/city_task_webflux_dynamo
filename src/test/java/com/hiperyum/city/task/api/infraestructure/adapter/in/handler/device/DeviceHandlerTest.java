package com.hiperyum.city.task.api.infraestructure.adapter.in.handler.device;

import com.hiperyum.city.task.api.ports.in.device.FindDeviceByIdUseCase;
import com.hiperyum.city.task.api.domain.model.Device;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DeviceHandlerTest {

    @Mock
    private FindDeviceByIdUseCase findDeviceByIdUseCase;

    @InjectMocks
    private DeviceHandler deviceHandler;

    @Autowired
    private WebTestClient webTestClient;

    @Value("${config.base.device.endpoint}")
    private String url;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDeviceById_Success(){
        String deviceId = "123";
        Device mockDevice = new Device(deviceId, "deviceName", "DeviceDescription", "ACTIVE");
        ServerRequest mockRequest = mock(ServerRequest.class);

        Mockito.when(mockRequest.pathVariable("id")).thenReturn(deviceId);
        Mockito.when(findDeviceByIdUseCase.execute(deviceId)).thenReturn(Mono.just(mockDevice));

        Mono<ServerResponse> response = deviceHandler.getDeviceById(mockRequest);

        StepVerifier
                .create(response)
                .consumeNextWith(serverResponse -> {
                    assert serverResponse.statusCode().is2xxSuccessful();
                    assert MediaType.APPLICATION_JSON.equals(serverResponse.headers().getContentType());
                })
                .verifyComplete();
        verify(findDeviceByIdUseCase,times(1)).execute(deviceId);
    }

    @Test
    void getDeviceById_Success2(){
        String deviceId = "321";
        Device mockDevice = new Device(deviceId, "deviceName", "DeviceDescription2", "ACTIVE");
        webTestClient
                .get()
                .uri(url.concat("/{id}"), Collections.singletonMap("id",deviceId))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(Device.class)
                .consumeWith(response -> {
                    Device d = response.getResponseBody();
                    assertTrue(d.getId().length()>0);
                    assertEquals("321", d.getId());
                });
    }

    @Test
    void getDeviceById_NotFound(){
        String deviceId = "456";
        ServerRequest mockRequest  = mock(ServerRequest.class);
        Mockito.when(mockRequest.pathVariable("id")).thenReturn(deviceId);
        Mockito.when(findDeviceByIdUseCase.execute(deviceId)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = deviceHandler.getDeviceById(mockRequest);

        StepVerifier.create(response)
                .consumeNextWith(serverResponse -> {
                    assert serverResponse.statusCode().is4xxClientError();
                })
                .verifyComplete();

        verify(findDeviceByIdUseCase, times(1)).execute(deviceId);
    }

}