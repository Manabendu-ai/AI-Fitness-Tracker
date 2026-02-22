package riku.fitness.apigateway.config;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import riku.fitness.apigateway.service.UserValidationService;
import riku.fitness.apigateway.service.dto.UserRequest;
import java.text.ParseException;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeyCloakUserSyncFilter implements WebFilter {

    private final UserValidationService service;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token == null) {
            return chain.filter(exchange);
        }

        UserRequest userReq = getUserDetails(token);

        if (userReq == null || userReq.getKeyCloakId() == null) {
            return chain.filter(exchange);
        }

        String userId = userReq.getKeyCloakId();

        return service.validate(userId)
                .doOnNext(exists -> log.info("VALIDATE RESULT for {} = {}", userId, exists))
                .flatMap(exists -> {
                    if (!exists) {
                        log.info("User not found. Registering...");
                        return service.register(userReq).then(); // 🔥 IMPORTANT
                    }
                    log.info("User already exists.");
                    return Mono.empty();
                })
                .then(Mono.defer(() -> {

                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header("X-User-ID", userId)
                            .build();

                    return chain.filter(
                            exchange.mutate().request(mutatedRequest).build()
                    );
                }));
    }


    private UserRequest getUserDetails(String token) {
        try{
            String tokenWithoutBearer = token.replace("Bearer", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            UserRequest UserReq = new UserRequest();
            UserReq.setEmail(claims.getClaimAsString("email"));
            UserReq.setKeyCloakId(claims.getClaimAsString("sub"));
            UserReq.setFirstName(claims.getClaimAsString("given_name"));
            UserReq.setLastName(claims.getClaimAsString("family_name"));
            UserReq.setPassword("pass@123");

            return UserReq;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
