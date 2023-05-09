package io.github.fernanda.maia.supplier.app.rest.resource;

import io.github.fernanda.maia.supplier.app.rest.dto.CompanyDTO;
import io.github.fernanda.maia.supplier.app.domain.model.Company;
import io.github.fernanda.maia.supplier.app.rest.dto.ErrorResponse;
import io.github.fernanda.maia.supplier.app.rest.dto.errors.ValidationError;
import io.github.fernanda.maia.supplier.app.domain.service.CompanyService;
import io.github.fernanda.maia.supplier.app.util.exceptions.BusinessException;

import jakarta.ws.rs.*;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.ConstraintViolation;

import jakarta.inject.Inject;

import lombok.RequiredArgsConstructor;

import java.util.*;

@Path(value = "/v1/companies")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class CompanyResourceV1 {

    private final CompanyService service;
    private final Validator validator;

    @GET
    public Response listAllCompanies() {
        return Response.ok(service.listAllCompanies()).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam(value = "id") Long id) {
        Response response;
        List<BusinessException> exceptions = new ArrayList<>();

        try {
            Company company = service.getById(id);
            response = Response.ok(company).build();

        } catch(BusinessException e) {
            exceptions.add(e);
            response = e.generateResponse(exceptions).status(e.getStatus());

        }

        return response;
    }

    @POST
    public Response createCompany(CompanyDTO request) {
        Response response;
        Set<ConstraintViolation<CompanyDTO>> violations = validator.validate(request);
        List<BusinessException> exceptions = new ArrayList<>();

        if(violations.isEmpty()) {
            try {
                Company newCompany = service.createCompany(request);
                response = Response.status(Response.Status.CREATED)
                        .entity(newCompany).build();
            } catch(BusinessException e) {
                exceptions.add(e);
                response = e.generateResponse(exceptions).status(e.getStatus());
            }


        } else {
            ErrorResponse errorResponse = ValidationError.generateResponse(violations);
            response = Response.status(errorResponse.getStatus())
                    .entity(errorResponse).build();
        }

        return response;
    }

    @DELETE
    @Path(value = "{id}")
    public Response deleteCompany(@PathParam(value = "id") Long id) {
        Response response;
        List<BusinessException> exceptions = new ArrayList<>();

        try {
            service.deleteCompany(id);
            response = Response.noContent().build();

        } catch(BusinessException e) {
            exceptions.add(e);
            response = e.generateResponse(exceptions).status(e.getStatus());
        }

        return response;
    }

    @PUT
    @Path(value = "{id}")
    public Response updateCompany(@PathParam(value = "id") Long id, CompanyDTO request) {
        Response response;
        List<BusinessException> exceptions = new ArrayList<>();

        Set<ConstraintViolation<CompanyDTO>> violations = validator.validate(request);

        if(violations.isEmpty()) {
            try {
                Company companyToUpdate = service.updateCompany(id, request);
                response = Response.ok(companyToUpdate).build();

            } catch(BusinessException e) {
                exceptions.add(e);
                response = e.generateResponse(exceptions).status(e.getStatus());
            }

        } else {
            response = ValidationError.generateResponse(violations)
                    .status(ErrorResponse.UNPROCESSABLE_ENTITY_STATUS);
        }

        return response;
    }

    @PATCH
    @Path("deactivate/{id}")
    public Response deactivateCompany(@PathParam(value = "id") Long id) {
        Response response;
        List<BusinessException> exceptions = new ArrayList<>();

        try {
            Company companyToDeactivate = service.setActive(id, false);
            response = Response.ok(companyToDeactivate).build();

        } catch(BusinessException e) {
            exceptions.add(e);
            response = e.generateResponse(exceptions).status(e.getStatus());
        }

        return response;
    }

    @PATCH
    @Path("activate/{id}")
    public Response activateCompany(@PathParam(value = "id") Long id) {
        Response response;
        List<BusinessException> exceptions = new ArrayList<>();

        try {
            Company companyToDeactivate = service.setActive(id, true);
            response = Response.ok(companyToDeactivate).build();

        } catch(BusinessException e) {
            exceptions.add(e);
            response = e.generateResponse(exceptions).status(e.getStatus());
        }

        return response;
    }
}
