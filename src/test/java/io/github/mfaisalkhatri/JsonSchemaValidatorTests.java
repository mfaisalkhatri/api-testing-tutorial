package io.github.mfaisalkhatri;

import io.github.mfaisalkhatri.data.CreateBookingData;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

import static io.github.mfaisalkhatri.data.CreateBookingDataBuilder.getBookingData;
import static io.restassured.RestAssured.given;

public class JsonSchemaValidatorTests {

    @Test
    public void testJsonSchemaOfCreateBooking() {

//        InputStream createBookingSchema = getClass()
//                .getClassLoader().getResourceAsStream("createbookingjsonschema.json");

        Path path = Path.of(System.getProperty("user.dir") + "/src/test/resources/createbookingjsonschema.json");

        File jsonSchemaFile = new File(path.toFile().toURI());

        CreateBookingData bookingData = getBookingData();

        given().when()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .log()
                .all()
                .body(bookingData)
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .log()
                .all()
                .statusCode(200)
                //.body(JsonSchemaValidator.matchesJsonSchema(Objects.requireNonNull(createBookingSchema)));
                .body(JsonSchemaValidator.matchesJsonSchema(jsonSchemaFile));

    }
}
