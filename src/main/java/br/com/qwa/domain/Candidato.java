package br.com.qwa.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record Candidato(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Sobrenome é obrigatório") String sobrenome,
        @NotBlank(message = "CPF é obrigatório") String cpf,
        @NotNull(message = "Data de nascimento é obrigatória") LocalDate dataNascimento) {
}
