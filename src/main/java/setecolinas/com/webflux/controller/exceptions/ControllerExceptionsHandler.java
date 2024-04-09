package setecolinas.com.webflux.controller.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ControllerExceptionsHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Mono<StandandError>> duplicateKeyException(
            DuplicateKeyException ex, ServerHttpRequest request
    ){
        return ResponseEntity.badRequest()
                .body(Mono.just(StandandError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(BAD_REQUEST.value())
                                .error(BAD_REQUEST.getReasonPhrase())
                                .message(verifyDupKeyException(ex.getMessage()))
                                .path(request.getPath().toString())
                        .build()));
    }

    private String verifyDupKeyException(String message){
        if(message.contains("email dup key")){
            return "Email already registered!";
        }
        return "Duplicate Key Exception";
    }
}
