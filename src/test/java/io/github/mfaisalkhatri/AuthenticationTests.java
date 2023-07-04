package io.github.mfaisalkhatri;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

import org.testng.annotations.Test;

public class AuthenticationTests {

    @Test
    public void testAPIKeyAuthentication () {
        int id = 2172797;
        given ().when ()
            .queryParam ("apiKey", System.getProperty ("apikey"))
            .queryParam ("id", id)
            .get ("https://api.openweathermap.org/data/2.5/weather")
            .then ()
            .body ("id", equalTo (id));
    }

    @Test
    public void testBasicAuth () {
        given ()
            .auth ()
            .basic ("postman", "password")
            .get ("https://postman-echo.com/basic-auth")
            .then ()
            .body ("$", hasKey ("authenticated"))
            .body ("authenticated", equalTo (true));
    }

    @Test
    public void testPreemptiveAuth() {
        given ()
            .auth ()
            .preemptive ()
            .basic ("postman", "password")
            .get ("https://postman-echo.com/basic-auth")
            .then ()
            .body ("$", hasKey ("authenticated"))
            .body ("authenticated", equalTo (true));
    }

    @Test
    public void testDigestAuth() {
        given ()
            .auth ()
            .digest ("postman", "password")
            .get ("https://postman-echo.com/basic-auth")
            .then ()
            .body ("$", hasKey ("authenticated"))
            .body ("authenticated", equalTo (true));
    }

}
