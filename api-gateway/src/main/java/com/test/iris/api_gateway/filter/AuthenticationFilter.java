package com.test.iris.api_gateway.filter;

import com.test.iris.api_gateway.exception.InvalidTokenException;
import com.test.iris.api_gateway.exception.UnauthorizedUserException;
import com.test.iris.api_gateway.util.JwtUtil;
import jakarta.ws.rs.core.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    @Autowired
    private RouteValidator validator;

    /*@Autowired
    private RestTemplate template;*/
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                ServerHttpRequest request = exchange.getRequest();
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
              // String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                logger.info("Request is secured",exchange.getRequest().getHeaders());
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new UnauthorizedUserException("Missing or invalid Authorization header.");
                }
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new UnauthorizedUserException("Missing or invalid Authorization header.");
                }



                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    logger.info("auth Header: ", authHeader);
//                    //REST call to AUTH service
                    // template.getForObject("http://AUTHENTICATION-SERVICE/validate?token" + authHeader, String.class);
                    String isValid = jwtUtil.validateToken(authHeader);
                    if (isValid.contains("payload={") && isValid.contains("signature=")) {
                        logger.info("Token is valid");

                    }
                    else {
                        throw new UnauthorizedUserException("Missing or invalid Authorization header.");
                    }
                }
               catch (UnauthorizedUserException ex){
                    logger.info("Missing or invalid Authorization header: {}",ex.getMessage());
                    throw new InvalidTokenException("Missing or invalid Authorization header");
                }
                catch (Exception e) {
                 logger.info("Exception occured Invalid Access to application  : {}",e.getMessage());
                    throw new UnauthorizedUserException("Missing or invalid Authorization header."); //new InvalidTokenException("Invalid Access to application-Unauthorized user or token has expired!!");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}