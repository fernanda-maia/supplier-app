package io.github.fernanda.maia.supplier.app.rest.resource;

import io.github.fernanda.maia.supplier.app.domain.model.Company;
import io.github.fernanda.maia.supplier.app.domain.repository.CompanyRepository;
import io.github.fernanda.maia.supplier.app.rest.dto.CompanyDTO;

import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.rest.dto.ValidationError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@Path(value = "/v1/companies")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CompanyResourceV1 {

    private final CompanyRepository repository;
    private final Validator validator;

    @GET
    public Response listAllCompanies() {
        PanacheQuery<Company> companies = repository.findAll();
        return Response.ok(companies.list()).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam(value = "id") Long id) {
        Response response;
        Company company = repository.findById(id);

        response = company != null?
                Response.ok(company).build() :
                Response.status(Response.Status.NOT_FOUND).build();

        return response;
    }

    @POST
    @Transactional
    public Response createCompany(CompanyDTO request) {
        Response response;
        Set<ConstraintViolation<CompanyDTO>> violations = validator.validate(request);

        if(violations.isEmpty()) {
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
            response = Response.ok(newCompany).build();

        } else {
            ErrorResponse errorResponse = ValidationError.generateResponse(violations);
            response = Response.status(errorResponse.getStatus())
                    .entity(errorResponse).build();
        }


        return response;
    }

    @DELETE
    @Transactional
    @Path(value = "{id}")
    public Response deleteCompany(@PathParam(value = "id") Long id) {
        Response response;
        Company companyToDelete = repository.findById(id);

        if(companyToDelete != null) {
            repository.delete(companyToDelete);
            response = Response.noContent().build();

        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }

        return response;
    }

    @PUT
    @Transactional
    @Path(value = "{id}")
    public Response updateCompany(@PathParam(value = "id") Long id, CompanyDTO request) {
        Response response;
        Company companyToUpdate = repository.findById(id);

        if(companyToUpdate != null) {
            companyToUpdate.setName(request.getName());
            companyToUpdate.setEmail(request.getEmail());
            companyToUpdate.setCep(request.getCep());

            response = Response.noContent().build();

        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }

        return response;
    }

    @PATCH
    @Transactional
    @Path("deactivate/{id}")
    public Response deactivateCompany(@PathParam(value = "id") Long id) {
        Response response;
        Company companyToDeactivate = repository.findById(id);

        if(companyToDeactivate != null) {
            companyToDeactivate.setActive(false);

            response = Response.noContent().build();

        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }

        return response;
    }

    @PATCH
    @Transactional
    @Path("activate/{id}")
    public Response activateCompany(@PathParam(value = "id") Long id) {
        Response response;
        Company companyToActivate = repository.findById(id);

        if(companyToActivate != null) {
            companyToActivate.setActive(true);

            response = Response.noContent().build();

        } else {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }

        return response;
    }
}
