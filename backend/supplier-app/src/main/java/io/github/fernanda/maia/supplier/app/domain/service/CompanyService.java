package io.github.fernanda.maia.supplier.app.domain.service;

import io.github.fernanda.maia.supplier.app.rest.dto.CompanyDTO;
import io.github.fernanda.maia.supplier.app.domain.model.Company;
import io.github.fernanda.maia.supplier.app.util.enums.ServiceType;
import io.github.fernanda.maia.supplier.app.util.exceptions.AlreadyRegisteredException;
import io.github.fernanda.maia.supplier.app.util.exceptions.BusinessException;
import io.github.fernanda.maia.supplier.app.util.exceptions.NotFoundException;
import io.github.fernanda.maia.supplier.app.domain.repository.CompanyRepository;

import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CompanyService {

    private final CompanyRepository repository;
    private final Validator validator;

    public List<Company> listAllCompanies() {
        return repository.findAll().list();
    }

    public Company getById(Long id) throws NotFoundException {
        return this.exists(id);
    }

    @Transactional
    public Company createCompany(CompanyDTO request) {
        checkUniqueness(request);
        Company newCompany = Company.builder()
                    .cpf(request.getCpf())
                    .cnpj(request.getCnpj())
                    .type(request.getType())
                    .name(request.getName())
                    .cep(request.getCep())
                    .email(request.getEmail())
                    .active(true)
                    .build();


        repository.persist(newCompany);

        return newCompany;
    }

    @Transactional
    public Company deleteCompany(Long id) throws NotFoundException {
        Company companyToDelete = this.exists(id);
        repository.delete(companyToDelete);

        return companyToDelete;
    }

    @Transactional
    public Company updateCompany(Long id, CompanyDTO request) throws NotFoundException {
        Company companyToUpdate = this.exists(id);
        checkUniqueness(request, id);

        companyToUpdate.setName(request.getName());
        companyToUpdate.setEmail(request.getEmail());
        companyToUpdate.setCep(request.getCep());

        return companyToUpdate;
    }

    @Transactional
    public Company setActive(Long id, boolean active) throws NotFoundException {
        Company company = this.exists(id);
        company.setActive(active);

        return company;
    }

    private Company exists(Long id) throws NotFoundException{
        Company company = repository.findById(id);

        if(company == null) {
            String message = String.format("Company with %d id not found", id);
            throw new NotFoundException(message, Company.class);
        }

        return company;
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

    private void checkUniqueness(CompanyDTO companyDTO) throws AlreadyRegisteredException {
        Map<String, String> docEntry = setDocValue(companyDTO);
        String field = docEntry.get("field");
        String value = docEntry.get("value");

        if(!field.isBlank() && !repository.list(field, value).isEmpty()) {
            String message = String.format("%s with number %s alredy registered", field.toUpperCase(), value);
            throw new AlreadyRegisteredException(message, Company.class);
        }
    }

    private void checkUniqueness(CompanyDTO companyDTO, Long id) throws AlreadyRegisteredException {
        Map<String, String> docEntry = setDocValue(companyDTO);
        String field = docEntry.get("field");
        String value = docEntry.get("value");

        if(!field.isBlank()) {
            Optional<Company> company = repository.find(field, value).firstResultOptional();

            if(company.isPresent() && !company.get().getId().equals(id)) {
                String message = String.format("%s with number %s alredy registered", field.toUpperCase(), value);
                throw new AlreadyRegisteredException(message, Company.class);
            }

        }
    }

}
