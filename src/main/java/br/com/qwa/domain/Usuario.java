package br.com.qwa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "username é obrigatorio")
    private String username;
    @NotBlank(message = "senha é obrigatoria")
    private String senha;
    @NotBlank(message = "role é obrigatoria")
    private String role;

    public Usuario() {
    }

    public Usuario(String username, String senha, String role) {
        this.username = username;
        this.senha = senha;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String roles) {
        this.role = roles;
    }
}
