package br.com.qwa.application.service;

import br.com.qwa.application.repository.CanditadoRepository;
import br.com.qwa.domain.Candidato;
import br.com.qwa.application.dto.CandidatoDTO;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.*;

@ApplicationScoped
public class CandidatoService {

    @Inject
    CanditadoRepository repository;

    public List<CandidatoDTO> listar(String nome) {
        return (nome != null) ? obterPorNome(nome)
                : CandidatoDTO.converterLista(repository.obterTodos());
    }

    public List<CandidatoDTO> obterPorNome(String nome) {
        return CandidatoDTO.converterLista(repository.obterPorNome(nome));
    }

    public Response obterPorCpf(String cpf) {
        Optional<Candidato> candidato = repository.obterPorCpf(cpf);
        if (candidato.isPresent()) {
            return Response.ok(new CandidatoDTO(candidato.get())).status(200).build();
        }
        return Response.status(NOT_FOUND).build();
    }

    public Response inserir(@Valid CandidatoDTO dto) {
        if (repository.inserir(dto.toEntity()).isPresent()) {
            return Response.ok(dto).build();
        }
        return Response.status(BAD_REQUEST).build();
    }

    public Response inserirLista(@Valid List<CandidatoDTO> dtoList) {
        repository.inserirLista(dtoList.stream().map(CandidatoDTO::toEntity).toList());
        return Response.status(OK).build();
    }

    public Response atualizar(String cpf, @Valid CandidatoDTO dto) {
        if (repository.atualizar(cpf, dto.toEntity()).isPresent()) {
            return Response.ok(dto).build();
        }
        return Response.status(NOT_ACCEPTABLE).build();
    }

    public Response deletar(String cpf) {
        if (repository.obterPorCpf(cpf).isPresent()) {
            repository.deletar(cpf);
            return Response.status(NO_CONTENT).build();
        }
        return Response.status(NOT_FOUND).build();
    }

}
