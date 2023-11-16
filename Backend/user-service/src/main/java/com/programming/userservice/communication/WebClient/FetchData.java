package com.programming.userservice.communication.WebClient;

import com.main.progamming.common.model.BaseModel;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class FetchData {
    @Autowired
    private WebClient webClient;

    public <T extends BaseModel> Mono<T> fetchDataFromAPI(String uri, Class<T> responseType) {
        return webClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getJwtToken())
                .retrieve()
                .bodyToMono(responseType);
    }

    private String getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }

        return null;
    }
}