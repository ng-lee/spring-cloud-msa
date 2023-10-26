package com.apigateway.filter;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    @Value("${token.secret}")
    private String secretKey;

    public AuthorizationHeaderFilter() {
        super(NameConfig.class);
    }

    @Override
    public GatewayFilter apply(NameConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer", "");

            if (!isJwtValid(jwt)) {
                return onError(exchange, "Jwt is not Valid", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };
    }

    private boolean isJwtValid(String jwt) {
        boolean returnValue = true;

        String subject = null;
        try {
            subject = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody().getSubject();
        } catch (Exception e) {
            returnValue = false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return returnValue;
    }

    // Mono, Flux -> spring 5.0 WebFlux
    // Spring Cloud Gateway Service는 스프링 MVC 패턴으로 구성되지 않음(HttpServletRequest, HttpServletResponse를 사용하지 않음)
    // WebFlux를 이용한 함수형 프로그래밍(?)으로 비동기 처리
    // WebFlux에서 사용하는 단위 값 -> Mono / 그 외는 Flux
    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(error);
        return response.setComplete();
    }
}
