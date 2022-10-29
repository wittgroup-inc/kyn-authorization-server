package com.wittgroup.kyn.authserver.services;

import com.wittgroup.kyn.authserver.client.UserClient;
import com.wittgroup.kyn.authserver.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    public UUID signUp(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("profile-management");
        Supplier<UUID> userSupplier = () -> userClient.signUp(user);
        return circuitBreaker.run(userSupplier, throwable -> handleSignupError());
    }

    public User loadUser(final String q) {
        RestTemplate template = new RestTemplate();
     // return  template.getForObject("http://localhost:8081/api/profiles/loadUser/pawan6186.sd@gmail.com", User.class);
        Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("profile-management");
        Supplier<User> addressSupplier = () -> userClient.loadUser(q);
        return circuitBreaker.run(addressSupplier, throwable -> handleUserServiceErrorCase());
    }

    private UUID handleSignupError()    {
        throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY);
    }

    private User handleUserServiceErrorCase() throws ResponseStatusException {
        throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY);
    }

}
