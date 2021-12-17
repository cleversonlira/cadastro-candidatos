package br.com.qwa.application.repository;

import br.com.qwa.domain.Candidato;

import java.util.List;
import java.util.Optional;

public interface CanditadoRepository {

    List<Candidato> obterTodos();

    Optional<Candidato> obterPorCpf(String cpf);

    List<Candidato> obterPorNome(String nome);

    Optional<Candidato> inserir(Candidato candidato);

    void inserirLista(List<Candidato> candidatos);

    Optional<Candidato> atualizar(String cpf, Candidato candidato);

    void deletar(String cpf);

}
