package com.programming.userservice.communication.WebClient;

import com.main.progamming.common.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
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
                .retrieve()
                .bodyToMono(responseType);
    }
}
