package com.nttdata;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

public class Hooks {

    @Before
    public void setupSSL() {
        System.out.println("-> Deshabilitando la validación de SSL");
        RestAssured.config = RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation());
    }
}
