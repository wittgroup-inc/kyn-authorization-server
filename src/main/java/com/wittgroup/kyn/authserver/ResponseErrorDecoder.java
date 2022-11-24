package com.wittgroup.kyn.authserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wittgroup.kyn.authserver.models.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ResponseErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponse message = null;
        try (InputStream bodyIs = response.body()
                .asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            log.debug("Error: "+inputStreamToString(bodyIs));
            message = mapper.readValue(bodyIs, ErrorResponse.class);
        } catch (IOException e) {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (HttpStatus.resolve(message.getHttpStatus()) != null) {
            return new ResponseStatusException(HttpStatus.resolve(message.getHttpStatus()), message.getMessage());
        } else {
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message.getMessage());
        }
    }

    private String inputStreamToString(InputStream is) {
        try {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
