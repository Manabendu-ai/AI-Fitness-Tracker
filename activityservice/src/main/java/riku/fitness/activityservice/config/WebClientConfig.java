package riku.fitness.activityservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced // very imp for interservice comm using the service names
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    // For communicating with USERSERVICE
    @Bean
    public WebClient userServiceWebClient(
            WebClient.Builder builder
    ){
        return builder.baseUrl("http://USERSERVICE").build();
    }
}
