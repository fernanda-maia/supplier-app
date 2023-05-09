package io.github.fernanda.maia.supplier.app.rest.dto.errors;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.util.exceptions.BusinessException;
import jakarta.validation.ConstraintViolation;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class ValidationError implements BusinessError {

    private static final String type = "Validation Error";
    private static final String reference = "https://docs.oracle.com/en/cloud/saas/marketing/eloqua-develop/Developers/GettingStarted/APIRequests/Validation-errors.htm";
    private static final String detail = "The request parameters failed to validate";

    private String field;
    private String reason;

    public static  <T> ErrorResponse generateResponse(Set<ConstraintViolation<T>> violations) {
        List<BusinessError> errors = violations.stream().map(c ->
                ValidationError.builder()
                        .field(c.getPropertyPath().toString())
                        .reason(c.getMessage())
                        .build()).collect(Collectors.toList());

        return ErrorResponse.builder()
                .type(type)
                .status(BusinessException.BAD_REQUEST)
                .reference(reference)
                .details(detail)
                .errors(errors)
                .build();
    }

}
