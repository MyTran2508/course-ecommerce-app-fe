package com.progamming.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/api/users/user/login",
            "/api/users/user/register",
            "/api/users/user/forget-password",
            "/api/users/user/send-otp",
            "/api/courses/course/download",
            "/eureka",
            "/api/courses/course/filter",
            "/api/courses/course/newest",
            "/api/courses/course/popular",
            "/api/courses/content/get-by-course-id",
            "/api/courses/course/get-all"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
