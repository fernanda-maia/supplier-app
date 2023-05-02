package io.github.fernanda.maia.supplier.app.domain.service;

import io.github.fernanda.maia.supplier.app.rest.dto.CompanyDTO;
import io.github.fernanda.maia.supplier.app.domain.model.Company;
import io.github.fernanda.maia.supplier.app.util.exceptions.NotFoundException;
import io.github.fernanda.maia.supplier.app.domain.repository.CompanyRepository;

import jakarta.inject.Inject;
import jakarta.validation.Validator;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.ApplicationScoped;

import lombok.RequiredArgsConstructor;

import java.util.List;

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
}
