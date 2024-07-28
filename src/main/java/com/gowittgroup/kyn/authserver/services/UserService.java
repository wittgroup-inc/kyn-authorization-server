package com.gowittgroup.kyn.authserver.services;

import com.gowittgroup.kyn.authserver.client.UserClient;
import com.gowittgroup.kyn.authserver.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserClient userClient;
    private final PasswordEncoder passwordEncoder;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    public UUID signUp(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Resilience4JCircuitBreaker circuitBreaker = (Resilience4JCircuitBreaker) circuitBreakerFactory.create("profile-management");
        Supplier<UUID> userSupplier = () -> userClient.signUp(user);
        return circuitBreaker.run(userSupplier, this::handleSignupError);
    }

    public User loadUser(final String q) {
        Resilience4JCircuitBreaker circuitBreaker = (Resilience4JCircuitBreaker) circuitBreakerFactory.create("profile-management");
        Supplier<User> addressSupplier = () -> userClient.loadUser(q);
        return circuitBreaker.run(addressSupplier, this::handleUserServiceErrorCase);
    }

    private UUID handleSignupError(Throwable t)    {
        log.debug(t.getMessage());
        throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, t.getMessage());
    }

    private User handleUserServiceErrorCase(Throwable t) throws ResponseStatusException {
        log.debug(t.getMessage());
        throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, t.getMessage());
    }

}
