package io.github.fernanda.maia.supplier.app.util.exceptions;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import jakarta.ws.rs.core.Response;

import java.util.List;

public abstract class BusinessException extends RuntimeException {
    public static final int NOT_FOUND = Response.Status.NOT_FOUND.getStatusCode();
    public static final int UNPROCESSABLE_ENTITY = 422;
    public static final int CONFLICT = Response.Status.CONFLICT.getStatusCode();
    public static final int BAD_REQUEST = Response.Status.BAD_REQUEST.getStatusCode();

    private Class<?> entity;
    private int status;

    public BusinessException(String message, Class<?> entity, int status) {
        super(message);
        this.entity = entity;
        this.status = status;
    }

    public abstract ErrorResponse generateResponse(List<BusinessException> e);

    public String getEntity() {
        return this.entity.getSimpleName();
    }

    public int getStatus() {
        return this.status;
    }
}
