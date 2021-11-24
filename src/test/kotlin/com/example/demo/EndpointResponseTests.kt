package com.example.demo

import io.restassured.RestAssured
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EndpointResponseTests(@LocalServerPort val port: Int) {


    @Test
    fun testDateAsc() {
        RestAssured.given().port(port)
                .log().all()
                .queryParam("order", "ASC")
                .get("/v1/resources")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ETAG, CoreMatchers.notNullValue())
                .header(HttpHeaders.CACHE_CONTROL, "max-age=10")
                .body("", CoreMatchers.notNullValue())
                .body("[0].id", CoreMatchers.`is`("123"))
                .body("[0].value1", CoreMatchers.`is`("some value"))
                .body("[0].value2", CoreMatchers.`is`(9000))
                .body("[0].createdAt", CoreMatchers.`is`("12:00:00"))
                .body("[1].id", CoreMatchers.`is`("456"))
                .body("[1].value1", CoreMatchers.`is`("another value"))
                .body("[1].value2", CoreMatchers.`is`(1337))
                .body("[1].createdAt", CoreMatchers.`is`("14:45:00"))

    }


    @Test
    fun testDateDesc() {
        RestAssured.given().port(port)
                .log().all()
                .queryParam("order", "DESC")
                .get("/v1/resources")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(HttpHeaders.ETAG, CoreMatchers.notNullValue())
                .header(HttpHeaders.CACHE_CONTROL, "max-age=10")
                .body("", CoreMatchers.notNullValue())
                .body("[0].id", CoreMatchers.`is`("456"))
                .body("[0].value1", CoreMatchers.`is`("another value"))
                .body("[0].value2", CoreMatchers.`is`(1337))
                .body("[0].createdAt", CoreMatchers.`is`("14:45:00"))
                .body("[1].id", CoreMatchers.`is`("123"))
                .body("[1].value1", CoreMatchers.`is`("some value"))
                .body("[1].value2", CoreMatchers.`is`(9000))
                .body("[1].createdAt", CoreMatchers.`is`("12:00:00"))
    }
}