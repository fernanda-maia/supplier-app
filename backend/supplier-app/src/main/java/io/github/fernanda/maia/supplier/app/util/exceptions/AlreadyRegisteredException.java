package io.github.fernanda.maia.supplier.app.util.exceptions;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.rest.dto.errors.AlreadyRegisteredError;

import java.util.List;

public class AlreadyRegisteredException extends BusinessException {
    public AlreadyRegisteredException(String message, Class<?> entity) {
        super(message, entity, 422);
    }

    @Override
    public ErrorResponse generateResponse(List<BusinessException> e) {
        return AlreadyRegisteredError.generateResponse(e);
    }
}
