package br.com.qwa.infrastructure.repository;

import br.com.qwa.application.repository.CanditadoRepository;
import br.com.qwa.domain.Candidato;
import io.agroal.api.AgroalDataSource;
import io.quarkus.logging.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CandidatoSQLRepository implements CanditadoRepository {

    @Inject
    AgroalDataSource dataSource;

    private final String INSERIR = """
            INSERT INTO candidatos(cpf, nome, sobrenome, data_nascimento)
            VALUES(?, ?, ?, ?);
            """;

    private final String OBTER_POR_CPF = """
            SELECT cpf, nome, sobrenome, data_nascimento
            FROM candidatos
            WHERE cpf = ?;
            """;
    private final String OBTER_POR_NOME = """
            SELECT cpf, nome, sobrenome, data_nascimento
            FROM candidatos
            WHERE nome LIKE '%?%' || sobrenome LIKE '%?%';
            """;

    private final String OBTER_TODOS = """
            SELECT cpf, nome, sobrenome, data_nascimento
            FROM candidatos;
            """;

    private final String DELETAR = "DELETE FROM candidatos WHERE cpf = ?;";

    private final String ATUALIZAR = """
            UPDATE candidatos
            SET cpf = ?,
                nome = ?,
                sobrenome = ?,
                data_nascimento = ?
            WHERE cpf = ?;
            """;


    @Override
    public List<Candidato> obterTodos() {
        Log.info("[CANDIDATO-REPOSITORY] Obtendo candidatos...");
        List<Candidato> candidatos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(OBTER_TODOS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                candidatos.add(preencherCandidato(resultSet));
            }
            Log.info("[CANDIDATO-REPOSITORY] Candidatos obtido com sucesso!");
        } catch (SQLException e) {
            Log.info("[CANDIDATO-REPOSITORY] Erro ao obter candidatos!!", e);
        }
        return candidatos;
    }

    @Override
    public Optional<Candidato> obterPorCpf(String cpf) {
        Log.info("[CANDIDATO-REPOSITORY] Obtendo candidato...");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(OBTER_POR_CPF)) {
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Log.info("[CANDIDATO-REPOSITORY] Candidato encontrado!");
                return Optional.of(preencherCandidato(resultSet));
            } else {
                Log.info("[CANDIDATO-REPOSITORY] Candidato não encontrado!");
            }
        } catch (SQLException e) {
            Log.info("[CANDIDATO-REPOSITORY] Erro ao obter candidato!!", e);
        }
        return Optional.of(null);
    }

    @Override
    public List<Candidato> obterPorNome(String nome) {
        Log.info("[CANDIDATO-REPOSITORY] Obtendo candidatos...");
        List<Candidato> candidatos = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(OBTER_POR_NOME)) {
            statement.setString(1, nome);
            statement.setString(2, nome);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                candidatos.add(preencherCandidato(resultSet));
            }
            Log.info("[CANDIDATO-REPOSITORY] Candidatos obtidos com sucesso!");
        } catch (SQLException e) {
            Log.info("[CANDIDATO-REPOSITORY] Erro ao obter candidatos!!", e);
        }
        return candidatos;
    }

    @Override
    public Optional<Candidato> inserir(Candidato candidato) {
        Log.info("[CANDIDATO-REPOSITORY] Inserindo candidato...");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERIR)) {
            preencherQuery(candidato, statement);
            statement.execute();
            Log.info("[CANDIDATO-REPOSITORY] Candidato inserido com sucesso!");
            return Optional.of(candidato);
        } catch (SQLException e) {
            Log.info("[CANDIDATO-REPOSITORY] Erro ao inserir candidato!!", e);
        }
        return Optional.of(null);
    }

    @Override
    public void inserirLista(List<Candidato> candidatos) {
        candidatos.forEach(this::inserir);
    }

    @Override
    public Optional<Candidato> atualizar(String cpf, Candidato candidato) {
        Log.info("[CANDIDATO-REPOSITORY] Atualizando candidato...");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(ATUALIZAR)) {
            preencherQuery(candidato, statement);
            statement.setString(5, cpf);
            statement.execute();
            Log.info("[CANDIDATO-REPOSITORY] Candidato atualizado com sucesso!");
            return Optional.of(candidato);
        } catch (SQLException e) {
            Log.info("[CANDIDATO-REPOSITORY] Erro ao inserir candidato!!", e);
        }
        return Optional.of(null);
    }

    @Override
    public void deletar(String cpf) {
        Log.info("[CANDIDATO-REPOSITORY] Removendo candidato...");
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETAR)) {
            statement.setString(1, cpf);
            statement.execute();
        } catch (SQLException e) {
            Log.info("[CANDIDATO-REPOSITORY] Erro ao remover candidato!!", e);
        }
    }

    private void preencherQuery(Candidato candidato, PreparedStatement statement) throws SQLException {
        statement.setString(1, candidato.getCpf());
        statement.setString(2, candidato.getNome());
        statement.setString(3, candidato.getSobrenome());
        statement.setDate(4, Date.valueOf(candidato.getDataNascimento()));
    }

    private Candidato preencherCandidato(ResultSet resultSet) throws SQLException {
        return Candidato.criar(
                resultSet.getString("nome"),
                resultSet.getString("sobrenome"),
                resultSet.getString("cpf"),
                resultSet.getDate("data_nascimento").toLocalDate());
    }
}
