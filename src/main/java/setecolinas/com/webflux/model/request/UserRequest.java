package setecolinas.com.webflux.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import setecolinas.com.webflux.validator.ValidatorUserRequest;

public record UserRequest(

        @ValidatorUserRequest //checks for whitespace at the beginning and end
        @Size(min = 4, max = 50, message = "must be between 4 and 50 characters")
        @NotBlank(message = "must not be null or empty")
        String name,

        @ValidatorUserRequest
        @Email(message = "invalitaded format field email")
        @NotBlank(message = "must not be null or empty")
        String email,

        @ValidatorUserRequest
        @Size(min = 4, max = 50, message = "must be between 4 and 30 characters")
        @NotBlank(message = "must not be null or empty")
        String password)
{}
