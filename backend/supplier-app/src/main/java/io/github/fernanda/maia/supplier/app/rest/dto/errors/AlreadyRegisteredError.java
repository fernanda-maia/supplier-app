package io.github.fernanda.maia.supplier.app.rest.dto.errors;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.util.exceptions.BusinessException;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class AlreadyRegisteredError implements BusinessError{
    private static final String type = "Already Registered Error";
    private static final String reference = "https://docs.oracle.com/en/";
    private static final String detail = "The entity is already registered";

    private String entity;
    private String reason;

    public static  <T> ErrorResponse generateResponse(List<BusinessException> violations) {
        List<BusinessError> errors = violations.stream().map(c ->
                AlreadyRegisteredError.builder()
                        .entity(c.getEntity())
                        .reason(c.getMessage())
                        .build()).collect(Collectors.toList());

        return ErrorResponse.builder()
                .type(type)
                .status(BusinessException.UNPROCESSABLE_ENTITY)
                .reference(reference)
                .details(detail)
                .errors(errors).build();

    }
}
