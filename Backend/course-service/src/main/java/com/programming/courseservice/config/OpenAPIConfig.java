package com.programming.courseservice.config;

import com.programming.courseservice.util.annotation.ShowOpenAPI;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getMethodAnnotation(ShowOpenAPI.class) == null) {
                // Ẩn hoàn toàn các phương thức không có chú thích @ApiShow
                return null;
            }
            return operation;
        };
    }
}
