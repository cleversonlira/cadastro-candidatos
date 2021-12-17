package br.com.qwa.domain;

import java.time.LocalDate;

public class Candidato {

    private final String nome;
    private final String sobrenome;
    private final String cpf;
    private final LocalDate dataNascimento;

    protected Candidato(String nome, String sobrenome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    public static Candidato criar(String nome, String sobrenome, String cpf, LocalDate dataNascimento) {
        return new Candidato(nome, sobrenome, cpf, dataNascimento);
    }

    public static Candidato criar(Candidato candidato) {
        return new Candidato(candidato.nome, candidato.sobrenome, candidato.cpf, candidato.dataNascimento);
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}
