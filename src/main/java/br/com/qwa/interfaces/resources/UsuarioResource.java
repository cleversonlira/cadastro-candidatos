package br.com.qwa.interfaces.resources;

import br.com.qwa.application.dto.UsuarioDTO;
import br.com.qwa.domain.Usuario;
import br.com.qwa.infrastructure.repository.UsuarioRepository;
import br.com.qwa.interfaces.dto.UsuarioFormDTO;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioRepository repository;

    @GET
    @Path("{id}")
    public Response buscar(@PathParam("id") Long id) {
        Optional<Usuario> usuario = repository.findByIdOptional(id);
        return usuario.isPresent()
                ? Response.ok(new UsuarioDTO(usuario.get())).build()
                : Response.status(404).build();
    }

    @GET
    public Response listar() {
        return Response.ok(UsuarioDTO.dtoList(repository.listAll())).build();
    }

    @POST
    @Transactional
    public Response inserir(@Valid UsuarioFormDTO dto) {
        Usuario usuario = dto.toEntity();
        repository.persist(usuario);
        return Response.ok(new UsuarioDTO(repository.findByUsername(usuario.getUsername()))).build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid Usuario atualizado) {
        Optional<Usuario> usuario = repository.findByIdOptional(id);
        return usuario.isPresent()
                ? Response.ok(new UsuarioDTO(usuario.get())).build()
                : Response.status(404).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response atualizar(@PathParam("id") Long id) {
        if (repository.findByIdOptional(id).isPresent()) {
            repository.deleteById(id);
            return Response.noContent().build();
        }
        return Response.status(404).build();
    }

}
