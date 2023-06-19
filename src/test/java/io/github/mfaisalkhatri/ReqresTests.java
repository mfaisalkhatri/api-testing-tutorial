package io.github.mfaisalkhatri;

import io.github.mfaisalkhatri.data.CreateUser;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTests {

    private static final String BASE_URL = "https://reqres.in";

    @Test
    public void testGetUsers() {
        given()
                .when()
                .queryParam("page", "2")
                .log()
                .all()
                .get(BASE_URL + "/api/users/")
                .then()
                .log()
                .all()
                .statusCode(200).body("page", equalTo(2))
                .body("data[1].id", equalTo(8))
                .body("data[1].last_name", equalTo("Ferguson"));
    }

    @Test
    public void testPostUser() {
        given().when().log().all().body("{\n" +
                        "    \"name\": \"Vinayak\",\n" +
                        "    \"job\": \"QA\"\n" +
                        "}").contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/api/users")
                .then()
                .log()
                .all()
                .statusCode(201)
                .body("name", equalTo("Vinayak"))
                .body("job", equalTo("QA"))
                .body("id", notNullValue())
                .body("createdAt",notNullValue());
    }

    @Test
    public void testPostUser_1() {

        CreateUser newUser = new CreateUser("Michael", "Manager");

        given().when().log().all().body(newUser).contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/api/users")
                .then()
                .log()
                .all()
                .statusCode(201)
                .body("name", equalTo(newUser.getName()))
                .body("job", equalTo(newUser.getJob()))
                .body("id", notNullValue())
                .body("createdAt",notNullValue());
    }

    @Test
    public void testUpdateUser() {
        CreateUser newUser = new CreateUser("Steve", "Test Engineer");
        given().when().log()
                .all()
                .body(newUser).contentType(ContentType.JSON)
                .when()
                .put(BASE_URL + "/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("name", equalTo(newUser.getName()))
                .body("job", equalTo(newUser.getJob()))
                .body("updatedAt", notNullValue());
    }

    @Test
    public void testUpdatePartialUser() {
        CreateUser newUser = new CreateUser("John", "SeniorTester");
        given().when()
                .log()
                .all()
                .body(newUser)
                .contentType(ContentType.JSON)
                .when()
                .patch(BASE_URL + "/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("name", equalTo(newUser.getName()))
                .body("job", equalTo(newUser.getJob()))
                .body("updatedAt", notNullValue());
    }
    @Test
    public void testDeleteUser() {

        given().when()
                .log()
                .all()
                .when()
                .delete(BASE_URL + "/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(204);
    }



}
