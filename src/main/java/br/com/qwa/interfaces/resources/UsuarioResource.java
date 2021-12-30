package br.com.qwa.interfaces.resources;

import br.com.qwa.application.dto.UsuarioDTO;
import br.com.qwa.domain.Usuario;
import br.com.qwa.infrastructure.repository.UsuarioRepository;
import br.com.qwa.interfaces.dto.UsuarioFormDTO;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioRepository repository;

    @GET
    @Path("{id}")
    @PermitAll
    public Response buscar(@PathParam("id") Long id) {
        Optional<Usuario> usuario = repository.findByIdOptional(id);
        return usuario.isPresent()
                ? Response.ok(new UsuarioDTO(usuario.get())).build()
                : Response.status(404).build();
    }

    @GET
    @PermitAll
    public Response listar() {
        return Response.ok(UsuarioDTO.dtoList(repository.listAll())).build();
    }

    @POST
    @Transactional
    @RolesAllowed({"User", "Admin"})
    public Response inserir(@Valid UsuarioFormDTO dto) {
        Usuario usuario = dto.toEntity();
        repository.persist(usuario);
        Optional<Usuario> usuarioInserido = Optional.ofNullable(repository.findByUsername(usuario.getUsername()));
        if (usuarioInserido.isPresent()) {
            URI uri = UriBuilder.fromUri("/topicos/{id}").build(usuarioInserido.get().getId());
            return Response.created(uri).build();
        }
        return Response.serverError().build();
    }

    @PUT
    @RolesAllowed({"User", "Admin"})
    @Transactional
    @Path("{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid UsuarioFormDTO atualizado) {
        Optional<Usuario> usuario = repository.findByIdOptional(id);
        return usuario.isPresent()
                ? Response.ok(new UsuarioDTO(usuario.get())).build()
                : Response.status(404).build();
    }

    @DELETE
    @RolesAllowed("Admin")
    @Transactional
    @Path("{id}")
    public Response deletar(@PathParam("id") Long id) {
        if (repository.findByIdOptional(id).isPresent()) {
            repository.deleteById(id);
            return Response.noContent().build();
        }
        return Response.status(404).build();
    }

}
