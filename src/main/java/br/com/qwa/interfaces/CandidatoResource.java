package br.com.qwa.interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/candidatos")
public class CandidatoResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void hello() {
    }
}