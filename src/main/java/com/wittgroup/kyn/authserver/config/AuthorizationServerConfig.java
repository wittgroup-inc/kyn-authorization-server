package com.wittgroup.kyn.authserver.config;


import lombok.RequiredArgsConstructor;
import org.apache.tomcat.Jar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;


@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final String CLIENT_ID = "kyn-cloud-gateway";
    private final String CLIENT_SECRET = "1232kyn123";
    private final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA47kmRZ2vxaNC9HFuURz7\n" +
            "9c7i+cU4vyd9G6PzKXd2+rYvNLQNphj1YzBDNQ4eI/OhX/MX1iVxC5j4Jz1Q10xN\n" +
            "iCea/6anMhBrW5i632b6alXyzjpTwbeqSAGWnqb5peVKp8WOJ8DXlEIaPqUqygtT\n" +
            "db3DMwjkTKDHpzQU1ZybKadPmLcH9TUtovlPOcD6ICgUbXlQrkOp8lN6ay9DEpY/\n" +
            "n49RA1IFXSgf6a43mpedEmQ0TCP3h/gOCfMMnZ9sU0/IUux3Y6d/ZCEM3GVz72wX\n" +
            "yq3tIPDQLuvCJqz15XrjDfPuKWrPYDAwLOLVXheWg5OE44NwKo/E44NiAPTOgulj\n" +
            "DwIDAQAB\n" +
            "-----END PUBLIC KEY-----";
    private final String PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEA47kmRZ2vxaNC9HFuURz79c7i+cU4vyd9G6PzKXd2+rYvNLQN\n" +
            "phj1YzBDNQ4eI/OhX/MX1iVxC5j4Jz1Q10xNiCea/6anMhBrW5i632b6alXyzjpT\n" +
            "wbeqSAGWnqb5peVKp8WOJ8DXlEIaPqUqygtTdb3DMwjkTKDHpzQU1ZybKadPmLcH\n" +
            "9TUtovlPOcD6ICgUbXlQrkOp8lN6ay9DEpY/n49RA1IFXSgf6a43mpedEmQ0TCP3\n" +
            "h/gOCfMMnZ9sU0/IUux3Y6d/ZCEM3GVz72wXyq3tIPDQLuvCJqz15XrjDfPuKWrP\n" +
            "YDAwLOLVXheWg5OE44NwKo/E44NiAPTOguljDwIDAQABAoIBAQDbQHl7z33b4Pvn\n" +
            "FIttZ2Wj3kSkzOF9Ek0fXrQQ+afBbcV3jeASSreAf4hFvckBPmHiWtBZa4Uy8F4E\n" +
            "kcGS+TEw5UkT2eXiivKCp0NNMv3pgcUcEAZyXh6B452cNRHqSyCc9dWPqtklMpvI\n" +
            "MwhGra72jvUw3t/Ic4inELr6jCTMbSxpMJN39yq1qrilSM71ZoOP2pZbVP1x8e+B\n" +
            "uNV5dhCYIm09zbPKUI1ZyPPSV/f9/eQ9ZK5NWi+XqmJq7B6JONvbCGJJ7L4Xac0T\n" +
            "+RDWdjLOKGcdMyYWWTUZDRhD2UmnS8g/j+SjmCdGVrjcGODEV7OhYdk4uB2gLLTT\n" +
            "6n6qWAjZAoGBAPZ3XwzkymATGXgiukxTlpus+lZwAE+xU+htTzB6C/zfEE6o1AbU\n" +
            "2GpWgwR/EHaX/qnMewft6C+y+iZaF8rZhqWD+AJZLTCaHQXmx6QjinAyCSBqdwDQ\n" +
            "e/FT87mLyv5IbKhh1s0uMo670qMRgDTf+RODGQNWNlh/pt9Iq/PXn2ODAoGBAOyI\n" +
            "LOdVCWSWvtFsEMKFobdu0lN4wdeZSanfjyLsr8GjL/uvzB2eNt96vmGZ06qJlC9L\n" +
            "JRjcz86O4zW0dzCBAXkH2+sfPvsBRisZ5QTYWlcz+1Y8A8Zx/aq734R1K/uJqv5H\n" +
            "lnVQYR+zgKvOdvpOtt8HKUkg7Q97HpW2dzdvi5CFAoGAKlOv0DAOjVdaXWZhZjBI\n" +
            "wi1/xI5xjz4d8eGr4uBi5KeOTmPyHcY58HExCPmlYyiRRKnoSyFRaber/CAmy+B1\n" +
            "nkE+u3CDMUCWUWgjyw2Lfd8p8BY4n7wuPPC0/52BXBAmEPjUgeI4LokVEIo14V7B\n" +
            "kNdH2WnZ5XLuvpPla/gQ2G8CgYEAjhaLqvfJabSoslNRihkOeYvIKz6Ka4J43uQe\n" +
            "LyOxvQfTxkTnwA8P9ZXdkfH/Vh3Jd9OTWGklKHdrhBKB2xBUGymecZLWmaA9T50B\n" +
            "l7SEKIhgfoAU4HwPnyW28vIUVslvf8rvVk/P2GRTcmHX1Su97SJ3Kafu4HLcf6EZ\n" +
            "4qJOW6UCgYBMxNvVZxhR7VwPQZLa1Wlrm/p/8/drH/UeA/IQ61u2UUWgumPw0hBw\n" +
            "eVVS+htg6C31SogcprUq0+pLBLYKqEf4JWYmbytytZIgTcYRPZ4N95ghDg7fvSBe\n" +
            "O5FYqK24ZjfZbkNcW7/kfyzwDPLxXIdyjBf9kW4OdQ3NspNA9qUJXg==\n" +
            "-----END RSA PRIVATE KEY-----";


    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient(CLIENT_ID)
                .secret(passwordEncoder.encode(CLIENT_SECRET))
                .scopes("read", "write")
                .accessTokenValiditySeconds(8000)
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .redirectUris("http://localhost:8087");

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancer());
    }

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(PRIVATE_KEY);
        converter.setVerifierKey(PUBLIC_KEY);
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }


}
