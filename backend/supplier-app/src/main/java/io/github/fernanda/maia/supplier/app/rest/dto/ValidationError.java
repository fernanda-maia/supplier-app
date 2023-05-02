package io.github.fernanda.maia.supplier.app.rest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.ws.rs.core.Response;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class ValidationError implements BusinessError {
    private static final int status = Response.Status.BAD_REQUEST.getStatusCode();
    private static final String type = "Validation Error";
    private static final String reference = "https://docs.oracle.com/en/cloud/saas/marketing/eloqua-develop/Developers/GettingStarted/APIRequests/Validation-errors.htm";
    private static final String detail = "The request parameters failed to validate";

    private String field;
    private String reason;

    public static <T> ErrorResponse generateResponse(Set<ConstraintViolation<T>> violations) {
        List<BusinessError> errors = violations.stream().map(c ->
                ValidationError.builder()
                        .field(c.getPropertyPath().toString())
                        .reason(c.getMessage())
                        .build()).collect(Collectors.toList());

        return ErrorResponse.builder()
                .type(type)
                .status(status)
                .reference(reference)
                .detail(detail)
                .errors(errors)
                .build();
    }
}
