package io.github.fernanda.maia.supplier.app.util.exceptions;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.rest.dto.errors.ActiveStateError;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class ActiveStateException extends BusinessException {
    public ActiveStateException(String message, Class<?> entity) {
        super(message, entity, Response.Status.CONFLICT.getStatusCode());
    }

    @Override
    public ErrorResponse generateResponse(List<BusinessException> e) {
        return ActiveStateError.generateResponse(e);
    }
}
