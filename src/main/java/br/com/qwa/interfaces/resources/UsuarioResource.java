package br.com.qwa.interfaces.resources;

import br.com.qwa.domain.Usuario;
import br.com.qwa.infrastructure.repository.UsuarioRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioRepository repository;

    @GET
    @Path("{id}")
    public Usuario buscar(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @GET
    public List<Usuario> listar() {
        return repository.listAll();
    }

    @POST
    @Transactional
    public Response inserir(@Valid Usuario usuario) {
        repository.persist(usuario);
        return Response.ok().build();
    }

    @PUT
    @Transactional
    @Path("{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid Usuario atualizado) {
        Optional<Usuario> usuario = repository.findByIdOptional(id);
        if (usuario.isPresent()) {
            usuario.get().setUsername(atualizado.getUsername());
            usuario.get().setSenha(atualizado.getSenha());
            usuario.get().setRole(atualizado.getRole());
            return Response.ok(usuario.get()).build();
        }
        return Response.status(404).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response atualizar(@PathParam("id") Long id) {
        Optional<Usuario> usuario = repository.findByIdOptional(id);
        if (usuario.isPresent()) {
            repository.deleteById(id);
            return Response.noContent().build();
        }
        return Response.status(404).build();
    }

}
