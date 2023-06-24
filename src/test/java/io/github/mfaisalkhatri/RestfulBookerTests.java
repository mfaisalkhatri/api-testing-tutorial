package io.github.mfaisalkhatri;

import io.github.mfaisalkhatri.data.CreateBookingData;
import org.testng.annotations.Test;

import static io.github.mfaisalkhatri.data.CreateBookingDataBuilder.getBookingData;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestfulBookerTests {

    private int bookingid;

    @Test
    public void testCreateBooking() {
        CreateBookingData bookingData = getBookingData();

        bookingid = given().when()
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
                .header("Content-Type", "application/json; charset=utf-8")
                .body("bookingid", is(notNullValue()))
                .body("booking.firstname", equalTo(bookingData.getFirstname()),
                       "booking.lastname", equalTo(bookingData.getLastname()),
                       "booking.bookingdates.checkin", equalTo(bookingData.getBookingdates().getCheckin()))
                .extract().path("bookingid");
    }

    @Test
    public void testGetBooking() {
        given().when()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .log()
                .all()
                .get("https://restful-booker.herokuapp.com/booking/" + bookingid)
                .then()
                .log()
                .all()
                .statusCode(200);
    }
}
