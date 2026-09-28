package br.ce.wcaquino.tasks.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";

    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.basePath = "/todo";

        RestAssured.given()
                    .log().all()
                .when()
                    .get()
                .then()
                    .log().all();
    }

    @Test
    public void deveAdicionarTarefaComSucesso() {
        RestAssured.basePath = "/todo";
        String body = "{\"task\": \"teste 2\", \"dueDate\": \"2026-09-29\"}";

        RestAssured.given()
                    .body(body)
                    .contentType(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .log().all()
                    .statusCode(201);
    }

    @Test
    public void naoDeveAdicionarTarefaInvalida() {
        RestAssured.basePath = "/todo";
        String body = "{\"task\": \"teste 2\", \"dueDate\": \"2026-09-21\"}";

        RestAssured.given()
                    .body(body)
                    .contentType(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .log().all()
                    .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}
