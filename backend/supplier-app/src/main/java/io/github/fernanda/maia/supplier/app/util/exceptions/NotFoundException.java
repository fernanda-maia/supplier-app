package io.github.fernanda.maia.supplier.app.util.exceptions;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.rest.dto.errors.NotFoundError;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message, Class<?> entity) {
        super(message, entity, Response.Status.NOT_FOUND.getStatusCode());
    }

    @Override
    public ErrorResponse generateResponse(List<BusinessException> e) {
        return NotFoundError.generateResponse(e);
    }
}
