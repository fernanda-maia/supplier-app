package io.github.fernanda.maia.supplier.app.domain.repository;

import io.github.fernanda.maia.supplier.app.domain.model.Company;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompanyRepository implements PanacheRepository<Company> {
}
