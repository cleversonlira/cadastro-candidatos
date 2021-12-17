package br.com.qwa.application.repository;

import br.com.qwa.domain.Candidato;

import java.util.List;

public interface CanditadoRepository {

    void inserir(Candidato candidato);

    Candidato obterPorCpf(String cpf);

    List<Candidato> obterPorNome(String nome);

    void atualizar(Candidato candidato);

    void deletar(String cpf);

}
