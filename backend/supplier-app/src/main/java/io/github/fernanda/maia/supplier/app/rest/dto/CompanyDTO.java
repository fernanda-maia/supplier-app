package io.github.fernanda.maia.supplier.app.rest.dto;

import io.github.fernanda.maia.supplier.app.util.enums.ServiceType;
import io.github.fernanda.maia.supplier.app.util.constraints.EnumAvailable;
import io.github.fernanda.maia.supplier.app.util.constraints.NotNullWhenType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NotNullWhenType(field = "cnpj", typeFieldName= "type", expectedType = "PJ", message = "CNPJ obrigatório para PJ")
@NotNullWhenType(field = "cpf", typeFieldName = "type", expectedType = "PF", message = "CPF obrigatório para PF")
public class CompanyDTO {
    @CPF(message = "CPF Inválido")
    private String cpf;

    @CNPJ(message = "CNPJ Inválido")
    private String cnpj;

    @EnumAvailable(field = "type", enumClass = ServiceType.class, message = "Tipo não encontrado")
    private String type;

    @NotBlank(message = "Campo nome é obrigatório")
    private String name;

    @NotBlank(message = "Campo cep é obrigatório")
    @Pattern(regexp = "^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$", message = "Formato de CEP inválido")
    private String cep;

    @Email(message = "Formato de e-mail inválido")
    @NotBlank(message = "campo e-mail é obrigatório")
    private String email;


}
