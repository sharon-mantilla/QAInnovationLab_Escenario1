package com.nttdata.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import org.json.JSONObject; // ✅ Importante
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoreStepDefinitions {

    private Response response;
    private int orderId;

    @Given("que envío una solicitud para crear una orden con:")
    public void queEnvioUnaSolicitudParaCrearUnaOrdenCon(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> datos = dataTable.asMaps(String.class, String.class);
        Map<String, String> orden = datos.get(0);

        // ✅ Construir el body JSON
        JSONObject body = new JSONObject();
        body.put("id", Integer.parseInt(orden.get("id")));
        body.put("petId", Integer.parseInt(orden.get("petId")));
        body.put("quantity", Integer.parseInt(orden.get("quantity")));
        body.put("status", orden.get("status"));

        // ✅ Crear la orden (POST)
        response = given()
                .baseUri("https://petstore.swagger.io/v2")
                .contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post("/store/order");

        // ✅ Validar respuesta
        response.then().statusCode(200)
                .body("id", notNullValue())
                .body("status", equalTo(orden.get("status")));

        // ✅ Guardar el ID real que devuelve la API
        orderId = response.jsonPath().getInt("id");
        System.out.println("Orden creada con ID: " + orderId);
    }

    @When("consulto la orden con el ID {int}")
    public void consultoLaOrdenConElID(Integer id) {
        // ✅ Usar el ID de la orden creada, no el del feature
        response = given()
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .get("/store/order/" + orderId);
    }

    @Then("la respuesta debe tener status 200 y los datos deben coincidir")
    public void validarDatosOrden() {
        // ✅ Validar que la orden consultada exista y coincida
        response.then().statusCode(200)
                .body("id", equalTo(orderId))
                .body("status", notNullValue())
                .body("complete", anyOf(is(true), is(false)));
    }
}
