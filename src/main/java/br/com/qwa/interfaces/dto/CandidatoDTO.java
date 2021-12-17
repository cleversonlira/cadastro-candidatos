package br.com.qwa.interfaces.dto;

import br.com.qwa.domain.Candidato;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record CandidatoDTO(
        @NotBlank(message = "Nome é obrigatório") String nome,
        @NotBlank(message = "Sobrenome é obrigatório") String sobrenome,
        @NotBlank(message = "CPF é obrigatório") String cpf,
        @NotNull(message = "Data de nascimento é obrigatória") LocalDate dataNascimento) {

    public Candidato toEntity() {
        return Candidato.criar(this.cpf, this.nome, this.sobrenome, this.dataNascimento);
    }

}
