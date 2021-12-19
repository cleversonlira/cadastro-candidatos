package br.com.qwa.interfaces.resources;

import br.com.qwa.application.service.CandidatoService;
import br.com.qwa.application.dto.CandidatoDTO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/candidatos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CandidatoResource {

    @Inject
    CandidatoService service;

    @GET
    public Response listar(@QueryParam("nome") String nome) {
        return service.listar(nome);
    }

    @GET
    @Path("{cpf}")
    public Response obterPorCpf(@PathParam("cpf") String cpf) {
        return service.obterPorCpf(cpf);
    }

    @POST
    public Response inserir(@Valid CandidatoDTO dto) {
        return service.inserir(dto);
    }

    @POST
    @Path("/inserir-lista")
    public Response inserirLista(@Valid List<CandidatoDTO> dtoList) {
        return service.inserirLista(dtoList);
    }

    @PUT
    @Path("/{cpf}")
    public Response atualizar(@PathParam("cpf") String cpf, @Valid CandidatoDTO dto) {
        return service.atualizar(cpf, dto);
    }

    @DELETE
    @Path("/{cpf}")
    public Response deletar(@PathParam("cpf") String cpf) {
        return service.deletar(cpf);
    }

}