package io.github.mfaisalkhatri;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.stringContainsInOrder;

import io.github.mfaisalkhatri.data.CreateUser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class ReqresTests extends SetupConfiguration {

    @Test
    public void testGetUsers() {
        given()
                .when()
                .queryParam("page", "2")
                .get("/api/users/")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data[1].id", equalTo(8))
                .body("data[1].last_name", equalTo("Ferguson"));

    }

    @Test
    public void testExtractValuesFromResponse() {
        String responseBody = given()
                .when()
                .queryParam("page", "2")
                .get("/api/users/")
                .getBody().asString();

        System.out.println(responseBody);

        String getBody = given()
                .when()
                .queryParam("page", "2")
                .get("/api/users/")
                .then()
                .statusCode(200)
                .extract().body().asString();

        System.out.println(getBody);

        String pageNo = given()
                .when()
                .queryParam("page", "2")
                .get("/api/users/")
                .then()
                .statusCode(200)
                .extract().path("page").toString();

        System.out.println(pageNo);

        String lastName = given()
                .when()
                .queryParam("page", "2")
                .get("/api/users/")
                .then()
                .statusCode(200)
                .extract().path("data[1].last_name").toString();

        System.out.println(lastName);
    }

    @Test
    public void testGetUsersMultiAssert() {
        given()
                .when()
                .queryParam("page", "2")
                .get("/api/users/")
                .then()
                .statusCode(200)
                .body("page", equalTo(2), "data[1].id",
                        equalTo(8), "data[1].last_name", equalTo("Ferguson"));

    }

    @Test
    public void testGetUserJsonExample() {
        String responseBody = given().when()
                .queryParam("page", "2")
                .get("/api/users/")
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
                .body("createdAt", is(notNullValue()));
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
                .body("createdAt", is(notNullValue()));
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
                .body("updatedAt", is(notNullValue()));
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
                .body("updatedAt", is(notNullValue()));
    }

    @Test
    public void testDeleteUser() {

        given().when()
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    public void testNumberAssertions() {
        given().when()
                .queryParam("page", 2)
                .get("/api/users/")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("page", equalTo(2))
                .body("per_page", greaterThan(4))
                .body("per_page", greaterThanOrEqualTo(6))
                .body("total", lessThan(14))
                .body("total_pages", lessThanOrEqualTo(3));
    }

    @Test
    public void testStringAssertions() {

        given()
                .get("/api/users/")
                .then()
                .assertThat()
                .body("data[0].first_name", equalTo("George"))
                .body("data[0].first_name", equalToIgnoringCase("GEOrge"))
                .body("data[0].email", containsString("george.bluth"))
                .body("data[0].last_name", startsWith("B"))
                .body("data[0].last_name", endsWith("h"))
                .body("data[1].first_name", equalToCompressingWhiteSpace("    Janet "));
    }

    @Test
    public void testHasKeyAssertion() {
        given()
                .get("/api/users/")
                .then()
                .and()
                .assertThat()
                .body("data[0]", hasKey("email"))
                .body("support", hasKey("url"))
                .body("$", hasKey("page"))
                .body("$", hasKey("total"));
    }

    @Test
    public void testNotAssertions () {
        given ().get ("/api/users/")
            .then ()
            .and ()
            .assertThat ()
            .body ("data", not (emptyArray ()))
            .body ("data[0].first_name", not (equalTo ("Vinayak")))
            .body ("data.size()", greaterThan (5));
    }

    @Test
    public void testPathParam () {

        int petId = 1;
        given ().when ()
            .pathParams ("petId",petId)
            .get ("https://petstore.swagger.io/v2/pet/{petId}")
            .then ()
            .statusCode (200)
            .and ()
            .assertThat ()
            .body ("id", equalTo (petId));
    }

    @Test
    public void testFormParam() {
        int petId = 1;
        FileReader fileReader = new FileReader();
        String fileName = "husky.jpg";

        given().when ().pathParam ("petId", petId)
            .formParam ("additionalMetadata", "Husky image")
            .contentType("multipart/form-data")
            .multiPart(fileReader.fileToUpload(fileName))
            .when()
            .post("https://petstore.swagger.io/v2/pet/{petId}/uploadImage")
            .then()
            .statusCode(200)
            .and ()
            .assertThat ()
            .body ("code", equalTo(200))
            .body ("message", containsString ("additionalMetadata: Husky image\nFile uploaded to ./husky.jpg"));
    }

}
