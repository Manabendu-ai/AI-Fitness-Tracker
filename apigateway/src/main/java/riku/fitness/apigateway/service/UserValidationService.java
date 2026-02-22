package riku.fitness.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import riku.fitness.apigateway.service.dto.UserRequest;
import riku.fitness.apigateway.service.dto.UserResponse;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient webClient;

    public Mono<Boolean> validate(String id){
        return webClient.get()
                .uri("/api/users/{id}/validate", id)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {

                    if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                        return Mono.error(new RuntimeException("User not found: " + id));

                    else if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Invalid: " + id));

                    return Mono.error(new RuntimeException("Unexpected error: " + id));
                });
    }

    public Mono<UserResponse> register(UserRequest userReq) {
        return webClient.post()
                .uri("/api/users/register")
                .bodyValue(userReq)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Bad Request: " + e.getMessage()));

                    return Mono.error(new RuntimeException("Unexpected error: " + e.getMessage()));
                });
    }
}
