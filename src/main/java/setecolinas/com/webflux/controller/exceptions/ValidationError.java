package setecolinas.com.webflux.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandandError {

    private final List<FieldError> errors = new ArrayList<>();

    ValidationError(LocalDateTime timestamp, String path, Integer status, String error, String message) {
        super(timestamp, path, status, error, message);
    }

    public void addError(String field, String message) {
        this.errors.add(new FieldError(field, message));
    }

    @Getter
    @AllArgsConstructor
    private static final class FieldError{
        private String field;
        private String message;
    }
}
