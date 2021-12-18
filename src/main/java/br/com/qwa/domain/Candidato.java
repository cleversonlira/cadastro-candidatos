package br.com.qwa.domain;

import java.time.LocalDate;

public record Candidato(
        String nome,
        String sobrenome,
        String cpf,
        LocalDate dataNascimento) {


    @Override
    public String toString() {
        return "Candidato{" +
               "nome='" + nome + '\'' +
               ", sobrenome='" + sobrenome + '\'' +
               ", cpf='" + cpf + '\'' +
               ", dataNascimento=" + dataNascimento +
               '}';
    }
}
