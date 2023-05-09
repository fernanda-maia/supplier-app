package io.github.fernanda.maia.supplier.app.domain.model;

import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cpf", "cnpj"}))
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String cnpj;

    private String type;
    private String name;
    private String cep;
    private String email;
    private Boolean active;
}
