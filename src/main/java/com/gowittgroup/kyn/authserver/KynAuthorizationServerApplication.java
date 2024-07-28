package com.gowittgroup.kyn.authserver;

import feign.codec.ErrorDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableFeignClients()
public class KynAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KynAuthorizationServerApplication.class, args);
    }
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ResponseErrorDecoder();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
