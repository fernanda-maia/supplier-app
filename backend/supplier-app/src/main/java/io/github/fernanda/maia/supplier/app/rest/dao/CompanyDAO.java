package io.github.fernanda.maia.supplier.app.rest.dao;

import io.github.fernanda.maia.supplier.app.domain.model.Company;
import io.github.fernanda.maia.supplier.app.util.enums.ServiceType;
import io.github.fernanda.maia.supplier.app.util.exceptions.NotFoundException;
import io.github.fernanda.maia.supplier.app.domain.repository.CompanyRepository;

import lombok.AllArgsConstructor;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor(onConstructor = @__(@Inject))
public class CompanyDAO implements DAO<Company>{
    private final CompanyRepository repository;
    @Override
    public List<Company> getAll() {
        return this.repository.findAll().list();
    }

    @Override
    public Optional<Company> getById(Long id) throws NotFoundException {
        Company company = repository.findById(id);
        return Optional.ofNullable(company);
    }

    @Override
    @Transactional
    public Optional<Company> save(Company object) {
        boolean isUnique = checkUniqueness(object);
        Optional<Company> company = Optional.empty();

        if(isUnique) {
            Company newCompany = Company.builder()
                    .cpf(object.getCpf())
                    .cnpj(object.getCnpj())
                    .type(object.getType())
                    .name(object.getName())
                    .cep(object.getCep())
                    .email(object.getEmail())
                    .active(true)
                    .build();

            repository.persist(newCompany);
            company = Optional.of(newCompany);
        }

        return company;
    }

    @Override
    @Transactional
    public Optional<Company> update(Long id, Company company) {
        Optional<Company> optionalCompany = this.getById(id);

        if(optionalCompany.isPresent()) {
            boolean isUnique = checkUniqueness(company, id);
            optionalCompany = Optional.empty();

            if(isUnique) {
                Company companyToUpdate = optionalCompany.get();

                company.setName(company.getName());
                companyToUpdate.setEmail(company.getEmail());
                companyToUpdate.setCep(company.getCep());

                optionalCompany = Optional.of(companyToUpdate);
            }

        }


        return optionalCompany;
    }

    @Override
    @Transactional
    public Long deleteById(Long id) {
        Long result = null;
        Optional<Company> company = this.getById(id);

        if(company.isPresent() && !company.get().getActive()) {
            repository.delete(company.get());
            result = id;
        }

        return result;
    }

    @Transactional
    public Optional<Company> setActiveStateCompany(Long id, boolean value) {
        Optional<Company> company = this.getById(id);
        company.ifPresent(company1 -> company1.setActive(value));

        return company;
    }

    private Map<String, String> setDocValue(Company company) {
        Map<String, String> result = new HashMap<>() {{
            put("field", "");
            put("value", null);
        }};

        String type = company.getType();

        if(type.equals(ServiceType.PJ.getDescription())) {
            company.setCpf(null);
            result.put("field", "cnpj");
            result.put("value", company.getCnpj());

        } else if (type.equals(ServiceType.PF.getDescription())) {
            company.setCnpj(null);
            result.put("field", "cpf");
            result.put("value", company.getCpf());
        }

        return result;
    }

    private boolean checkUniqueness(Company company) {
        Map<String, String> docEntry = setDocValue(company);
        String field = docEntry.get("field");
        String value = docEntry.get("value");

        return !field.isBlank() && repository.list(field, value).isEmpty();
    }

    private boolean checkUniqueness(Company company, Long id){
        boolean result = false;

        Map<String, String> docEntry = setDocValue(company);
        String field = docEntry.get("field");
        String value = docEntry.get("value");

        if(!field.isBlank()) {
            Optional<Company> companyFound = repository.find(field, value).firstResultOptional();
            result = (companyFound.isPresent() && companyFound.get().getId().equals(id)) || companyFound.isEmpty();
        }

        return result;
    }
}
