package io.github.fernanda.maia.supplier.app.domain.service;

import io.github.fernanda.maia.supplier.app.rest.dao.CompanyDAO;
import io.github.fernanda.maia.supplier.app.rest.dto.CompanyDTO;
import io.github.fernanda.maia.supplier.app.domain.model.Company;
import io.github.fernanda.maia.supplier.app.util.enums.ServiceType;
import io.github.fernanda.maia.supplier.app.util.exceptions.ActiveStateException;
import io.github.fernanda.maia.supplier.app.util.exceptions.AlreadyRegisteredException;
import io.github.fernanda.maia.supplier.app.util.exceptions.NotFoundException;

import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.enterprise.context.ApplicationScoped;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CompanyService {

    private final CompanyDAO companyDAO;
    private final Validator validator;

    public List<Company> listAllCompanies() {
        return companyDAO.getAll();
    }

    public Company getById(Long id) {
        return this.exists(id);
    }

    public Company createCompany(CompanyDTO request) {
        Company newCompany = Company.builder()
                    .cpf(request.getCpf())
                    .cnpj(request.getCnpj())
                    .type(request.getType())
                    .name(request.getName())
                    .cep(request.getCep())
                    .email(request.getEmail())
                    .active(true)
                    .build();

        String message = createMessage(request);

        return companyDAO.save(newCompany)
                .orElseThrow(() -> new AlreadyRegisteredException(message, Company.class));
    }

    public Company deleteCompany(Long id) throws NotFoundException {
        Company companyToDelete = this.exists(id);
        Long companyId = companyDAO.deleteById(id);

        if(companyId == null) {
            throw new ActiveStateException("The company must be deactivated before delete", Company.class);
        }

        return companyToDelete;
    }

    public Company updateCompany(Long id, CompanyDTO request)  {
        Company company = this.exists(id);

        company.setCpf(request.getCpf());
        company.setCnpj(request.getCnpj());
        company.setType(request.getType());
        company.setEmail(request.getEmail());
        company.setCep(request.getCep());
        company.setName(request.getName());

        String message = createMessage(request);

        return companyDAO.update(id, company)
                .orElseThrow(() -> new AlreadyRegisteredException(message, Company.class));
    }

    public Company setActive(Long id, boolean active) {
        Company company = this.exists(id);
        company = companyDAO.setActiveStateCompany(id, active).orElse(null);

        return company;
    }

    private Company exists(Long id) throws NotFoundException{
        String message = String.format("Company with %d id not found", id);
        return companyDAO.getById(id)
                .orElseThrow(() -> new NotFoundException(message, Company.class));

    }

    private Map<String, String> setDocValue(CompanyDTO companyDTO) {
        Map<String, String> result = new HashMap<>() {{
            put("field", "");
            put("value", null);
        }};

        String type = companyDTO.getType();

        if(type.equals(ServiceType.PJ.getDescription())) {
            companyDTO.setCpf(null);
            result.put("field", "cnpj");
            result.put("value", companyDTO.getCnpj());

        } else if (type.equals(ServiceType.PF.getDescription())) {
            companyDTO.setCnpj(null);
            result.put("field", "cpf");
            result.put("value", companyDTO.getCpf());
        }

        return result;
    }

    private String createMessage(CompanyDTO companyDTO) {
        String message = "";
        Map<String, String> docEntry = setDocValue(companyDTO);
        String field = docEntry.get("field");
        String value = docEntry.get("value");

        if(!field.isBlank()) {
            message = String.format("%s with number %s already registered", field.toUpperCase(), value);
        }

        return message;
    }

}
