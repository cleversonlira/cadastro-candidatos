package br.com.qwa.interfaces.resources.sucesso;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CandidatoResourceSuccessTest {
    private final String NOME_EXISTENTE = "Fernanda";
    private String CPF_EXISTENTE = "92862083089";
    private String CPF_INEXISTENTE = "00000000000";

    @Test
    public void testeListarCanditado() {
        given()
                .when().get("/candidatos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testeListarCanditadoPorNome() {
        given()
                .when().get("/candidatos?nome=" + this.NOME_EXISTENTE)
                .then()
                .statusCode(200);
    }

    @Test
    public void testeObterCanditadoPorCPF() {
        given()
                .when().get("/candidatos/" + this.CPF_EXISTENTE)
                .then()
                .statusCode(200);
    }

    @Test
    public void testeDeletarCandidato() {
        given()
                .when().delete("/candidatos/" + this.CPF_INEXISTENTE)
                .then()
                .statusCode(404);
    }

}
