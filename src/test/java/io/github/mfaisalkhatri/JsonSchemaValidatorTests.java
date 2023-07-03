package io.github.mfaisalkhatri;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.github.mfaisalkhatri.data.CreateBookingData;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

import static io.github.mfaisalkhatri.data.CreateBookingDataBuilder.getBookingData;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;

public class JsonSchemaValidatorTests {

    @Test
    public void testJsonSchemaOfCreateBooking() {

//        InputStream createBookingSchema = getClass()
//                .getClassLoader().getResourceAsStream("createbookingjsonschema.json");

        Path path = Path.of(System.getProperty("user.dir") + "/src/test/resources/createbookingjsonschema.json");

        File jsonSchemaFile = new File(path.toFile().toURI());

        JsonSchemaFactory factory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(
                        ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV3)
                                .freeze()).freeze();

        JsonSchemaValidator.settings = settings()
                .with().jsonSchemaFactory(factory)
                .and().with().checkedValidation(false);

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
                //.body(JsonSchemaValidator.matchesJsonSchema(jsonSchemaFile).using(jsonSchemaFactory))
                .body(JsonSchemaValidator.matchesJsonSchema(jsonSchemaFile).using(settings().with().checkedValidation(false)));

    }
}
