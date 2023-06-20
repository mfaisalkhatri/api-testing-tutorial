package io.github.mfaisalkhatri;

import io.github.mfaisalkhatri.data.CreateUser;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTests extends SetupConfiguration {

    @Test
    public void testGetUsers() {
        given()
                .when()
                .queryParam("page", "2")
                .get("/api/users/")
                .then()
                .statusCode(200).body("page", equalTo(2))
                .body("data[1].id", equalTo(8))
                .body("data[1].last_name", equalTo("Ferguson"));
    }

    @Test
    public void testGetUserJsonExample() {
        String responseBody = given ().when ()
                .queryParam ("page", "2")
                .get ("/api/users/")
                .getBody().asString();

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONObject dataObject = jsonArray.getJSONObject(0);
        String firstName = dataObject.get("first_name").toString();
        System.out.println(firstName);
    }

    @Test
    public void testPostUser() {
        given().when().body("{\n" +
                        "    \"name\": \"Vinayak\",\n" +
                        "    \"job\": \"QA\"\n" +
                        "}")
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Vinayak"))
                .body("job", equalTo("QA"))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    public void testPostUser_1() {

        CreateUser newUser = new CreateUser("Michael", "Manager");

        given().when().body(newUser)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", equalTo(newUser.getName()))
                .body("job", equalTo(newUser.getJob()))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    public void testUpdateUser() {
        CreateUser newUser = new CreateUser("Steve", "Test Engineer");
        given().when().body(newUser)
                .when()
                .put("/api/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo(newUser.getName()))
                .body("job", equalTo(newUser.getJob()))
                .body("updatedAt", notNullValue());
    }

    @Test
    public void testUpdatePartialUser() {
        CreateUser newUser = new CreateUser("John", "SeniorTester");
        given().when()
                .body(newUser)
                .when()
                .patch("/api/users/2")
                .then()
                .statusCode(200)
                .body("name", equalTo(newUser.getName()))
                .body("job", equalTo(newUser.getJob()))
                .body("updatedAt", notNullValue());
    }

    @Test
    public void testDeleteUser() {

        given().when()
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }


}
