package br.com.qwa.application.dto;

import br.com.qwa.domain.Candidato;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CandidatoDTO {

    @NotBlank(message = "Nome é obrigatório")
    public String nome;
    @NotBlank(message = "Sobrenome é obrigatório")
    public String sobrenome;
    @CPF
    public String cpf;
    @NotNull(message = "Data de nascimento é obrigatória")
    public String dataNascimento;

    public CandidatoDTO() {
    }

    public CandidatoDTO(Candidato candidato) {
        this.nome = candidato.nome();
        this.sobrenome = candidato.sobrenome();
        this.cpf = candidato.cpf();
        this.dataNascimento = candidato.dataNascimento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static List<CandidatoDTO> converterLista(List<Candidato> candidatos) {
        return candidatos.stream().map(CandidatoDTO::new).toList();
    }

    public Candidato toEntity() {
        return new Candidato(
                this.nome,
                this.sobrenome,
                tratarCpf(),
                tratarDataNascimento());
    }

    private LocalDate tratarDataNascimento() {
        try {
            return LocalDate.parse(this.dataNascimento, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            return LocalDate.parse(this.dataNascimento, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    private String tratarCpf() {
        return this.cpf.replace(".", "").replace("-", "");
    }
}
