package br.com.qwa.interfaces;

import br.com.qwa.application.repository.CanditadoRepository;
import br.com.qwa.domain.Candidato;
import br.com.qwa.interfaces.dto.CandidatoDTO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.*;

@Path("/candidatos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CandidatoResource {

    @Inject
    CanditadoRepository repository;

    @GET
    public List<Candidato> listar() {
        return repository.obterTodos();
    }

    @GET
    @Path("/{nome}")
    public List<Candidato> obterPorNome(@PathParam("nome") String nome) {
        return repository.obterPorNome(nome);
    }

    @GET
    @Path("/{cpf}")
    public Response obterPorCpf(@PathParam("cpf") String cpf) {
        Optional<Candidato> optionalCandidato = repository.obterPorCpf(cpf);
        if (optionalCandidato.isPresent()) {
            return Response.ok().status(200).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    @POST
    public Response inserir(@Valid CandidatoDTO dto) {
        Candidato candidato = dto.toEntity();
        if (repository.inserir(candidato).isPresent()) {
            return Response.ok(dto.toEntity()).build();
        }
        return Response.status(BAD_REQUEST).build();
    }

    @POST
    public Response inserirLista(@Valid List<CandidatoDTO> dtoList) {
        repository.inserirLista(dtoList.stream().map(c -> Candidato.criar(c.toEntity())).toList());
        return Response.status(BAD_REQUEST).build();
    }

    @PUT
    @Path("/{cpf}")
    public Response atualizar(@PathParam("cpf") String cpf, @Valid CandidatoDTO dto) {
        Candidato candidato = dto.toEntity();
        if (repository.atualizar(cpf, candidato).isPresent()) {
            return Response.ok(dto.toEntity()).build();
        }
        return Response.status(NOT_MODIFIED).build();
    }

    @DELETE
    @Path("/{cpf}")
    public Response deletar(@PathParam("cpf") String cpf) {
        if (repository.obterPorCpf(cpf).isPresent()) {
            repository.deletar(cpf);
            return Response.status(NO_CONTENT).build();
        }
        return Response.status(NOT_FOUND).build();
    }

}