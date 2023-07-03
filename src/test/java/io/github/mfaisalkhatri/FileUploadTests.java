package io.github.mfaisalkhatri;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FileUploadTests {


    @Test
    public void testFileUpload() {

        String URL = "http://postman-echo.com/post";
        String fileName = "calc.csv";
        FileReader fileReader = new FileReader();

        given().when()
                .log()
                .all()
                .contentType("multipart/form-data")
                .multiPart(fileReader.fileToUpload(fileName))
                .when()
                .post(URL)
                .then()
                .statusCode(200)
                .log()
                .all()
                .and()
                .assertThat()
                .body("files", hasKey(fileName), "files.'calc.csv'", is(notNullValue()));


    }
}
