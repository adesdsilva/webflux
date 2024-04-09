package setecolinas.com.webflux.controller.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StandandError {
    private LocalDateTime timestamp;
    private String path;
    private  Integer status;
    private String error;
    private String message;
}
