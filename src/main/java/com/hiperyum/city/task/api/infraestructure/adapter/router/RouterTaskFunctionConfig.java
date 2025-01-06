package com.hiperyum.city.task.api.infraestructure.adapter.router;

import com.hiperyum.city.task.api.infraestructure.adapter.in.handler.task.TaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterTaskFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(TaskHandler taskHandler) {
        return route(GET("/api/tasks/{id}"), taskHandler::findTaskById)
                .andRoute(POST("/api/tasks/"), taskHandler::createTask)
                .andRoute(PUT("/api/tasks/"), taskHandler::updateTask);
    }

}
