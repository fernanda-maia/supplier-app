package io.github.fernanda.maia.supplier;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path(value = "/supplier")
public class SupplierApp {

    @GET
    @Produces(value = MediaType.TEXT_PLAIN)
    public String getMessage() {
        return "Hello, Quarkus!";
    }
}
