package br.com.qwa.interfaces.resources.falha;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CandidatoResourceErrorTest {
    private final String NOME_INEXISTENTE = "Orochimaru";
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
                .when().get("/candidatos?nome=" + NOME_INEXISTENTE)
                .then()
                .statusCode(204);
    }

    @Test
    public void testeObterCanditadoPorCPF() {
        given()
                .when().get("/candidatos/" + CPF_INEXISTENTE)
                .then()
                .statusCode(404);
    }

    @Test
    public void testeDeletarCandidato() {
        given()
                .when().delete("/candidatos/" + this.CPF_INEXISTENTE)
                .then()
                .statusCode(404);
    }

}
