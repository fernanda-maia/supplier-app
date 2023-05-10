package io.github.fernanda.maia.supplier.app.rest.dto.errors;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.util.exceptions.BusinessException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ActiveStateError {
    private static final String type = "Active State Error";
    private static final String reference = "https://docs.oracle.com/en";
    private static final String detail = "Cannot perform the action with the current Active state";

    private String entity;
    private String reason;

    public static  <T> ErrorResponse generateResponse(List<BusinessException> violations) {
        List<BusinessError> errors = violations.stream().map(c ->
                NotFoundError.builder()
                        .entity(c.getEntity())
                        .reason(c.getMessage())
                        .build()).collect(Collectors.toList());


        return ErrorResponse.builder()
                .type(type)
                .status(BusinessException.CONFLICT)
                .reference(reference)
                .details(detail)
                .errors(errors)
                .build();
    }
}
