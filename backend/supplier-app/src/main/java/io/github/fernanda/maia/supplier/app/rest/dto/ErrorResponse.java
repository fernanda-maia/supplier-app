package io.github.fernanda.maia.supplier.app.rest.dto;

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

    private String reference;
    private String type;
    private int status;
    private String detail;
    private List<BusinessError> errors;

}
