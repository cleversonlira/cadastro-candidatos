package br.com.qwa.interfaces.dto;

import br.com.qwa.domain.Usuario;

import javax.validation.constraints.NotBlank;

public class UsuarioFormDTO {

    @NotBlank(message = "username é obrigatorio")
    private String username;
    @NotBlank(message = "senha é obrigatoria")
    private String senha;
    @NotBlank(message = "role é obrigatoria")
    private String role;

    public Usuario toEntity() {
        return new Usuario(this.username, this.senha, this.role);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
