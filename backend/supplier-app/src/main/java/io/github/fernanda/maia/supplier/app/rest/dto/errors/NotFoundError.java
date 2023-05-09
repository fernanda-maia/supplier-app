package io.github.fernanda.maia.supplier.app.rest.dto.errors;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.util.exceptions.BusinessException;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class NotFoundError implements BusinessError {
    private static final String type = "Not Found Error";
    private static final String reference = "https://docs.oracle.com/en/cloud/saas/marketing/eloqua-develop/Developers/GettingStarted/APIRequests/Validation-errors.htm";
    private static final String detail = "The queried element does not exists";

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
                .status(BusinessException.NOT_FOUND)
                .reference(reference)
                .details(detail)
                .errors(errors)
                .build();
    }
}
