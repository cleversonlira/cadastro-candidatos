package br.com.qwa.application.dto;

import br.com.qwa.domain.Usuario;

import java.util.List;

public class UsuarioDTO {

    private Long id;
    private String username;
    private String role;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.role = usuario.getRole();
    }

    public static List<UsuarioDTO> dtoList(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioDTO::new).toList();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

}
