package br.com.qwa.application.dto;

import br.com.qwa.domain.Usuario;

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
