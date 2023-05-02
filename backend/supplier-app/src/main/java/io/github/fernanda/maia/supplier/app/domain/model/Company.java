package io.github.fernanda.maia.supplier.app.domain.model;

import io.github.fernanda.maia.supplier.app.util.enums.ServiceType;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cpf", "cnpj"}))
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cpf;
    private String cnpj;
    private String type;
    private String name;
    private String cep;
    private String email;
    private Boolean active;
}
