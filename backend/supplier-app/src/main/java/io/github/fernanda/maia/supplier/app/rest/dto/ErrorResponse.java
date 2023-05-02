package io.github.fernanda.maia.supplier.app.rest.dto;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    public static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    private String reference;
    private String type;
    private int status;
    private String detail;
    private List<BusinessError> errors;

    public Response status(int code) {
        this.setStatus(code);

        return Response.status(code)
                .entity(this).build();
    }

}
