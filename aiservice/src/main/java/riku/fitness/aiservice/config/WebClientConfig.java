//package riku.fitness.aiservice.config;
//
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
//public class WebClientConfig {
//
//    @Bean
//    @LoadBalanced
//    public WebClient.Builder webClientBuilder(){
//        return WebClient.builder();
//    }
//
//    @Bean
//    public WebClient userServiceWebClient(
//            WebClient.Builder builder
//    ){
//        return builder.baseUrl("http://USERSERVICE")
//                .build();
//    }
//
//    @Bean
//    public WebClient activityServiceWebClient(
//            WebClient.Builder builder
//    ){
//        return builder.baseUrl("http://ACTIVITYSERVICE")
//                .build();
//    }
//}
