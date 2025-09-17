package in.ashar.api_gateway.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Component
public class JwtFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${gateway.header}")
    private String gatewayHeader;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("inside api filter");

        String path = exchange.getRequest().getPath().toString();

        // Allow public endpoints
        if (path.startsWith("/auth") || path.startsWith("/public")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            System.out.println("error 1");
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // check token type
            if(!claims.get("type", String.class).equals("active")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                System.out.println("error 2");
                return exchange.getResponse().setComplete();
            }

            // Optionally, add claims to headers for downstream services

            List<?> roles = claims.get("role", List.class);
            String role = roles.stream().map(Object::toString).toList().toString();

            exchange.getRequest().mutate()
                    .header("X-USER-NAME", claims.getSubject())
                    .header("X-USER-ROLE",role)
                    .header("X-GATEWAY-REQUEST",gatewayHeader)
                    .build();

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            System.out.println("error 3: "+e.getMessage());
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Run before other filters
    }
}
