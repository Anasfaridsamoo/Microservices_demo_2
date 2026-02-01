package com.demo.catalogservice.web.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.demo.catalogservice.AbstractIT;
import com.demo.catalogservice.domain.ProductResponse;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-data.sql")
public class ProductControllerTest extends AbstractIT {
    @Test
    public void shouldReturnProducts() {
        given().contentType(ContentType.JSON)
                .log()
                .all()
                .when()
                .get("/api/products/all")
                .then()
                .statusCode(200)
                .body("data", hasSize(10))
                .body("totalElements", is(15))
                .body("pageNumber", is(1))
                .body("totalPages", is(2))
                .body("isFirst", is(true))
                .body("isLast", is(false))
                .body("hasNext", is(true))
                .body("hasPrevious", is(false));
    }

    @Test
    public void shouldGetProductByCode() {
        ProductResponse product = given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "P100")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(ProductResponse.class);

        assertThat(product.code().equals("P100"));
        assertThat(product.name().equals("The Hunger Games"));
        assertThat(product.description().equals("Winning will make you famous. Losing means certain death..."));
        assertThat(product.price().compareTo(BigDecimal.valueOf(34.0)));
    }

    @Test
    void shouldReturnNotFoundForInvalidCode() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", "INVALID_CODE")
                .then()
                .statusCode(404)
                .body("status", is(404))
                .body("title", is("Product Not Found"))
                .body("detail", is("Product with code INVALID_CODE not found"));
    }
}
