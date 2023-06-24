package io.github.mfaisalkhatri.data;

import io.github.mfaisalkhatri.data.BookingDates;
import io.github.mfaisalkhatri.data.CreateBookingData;
import net.datafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class CreateBookingDataBuilder {

    private static final Faker FAKER = new Faker();

    public static CreateBookingData getBookingData() {

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

        return CreateBookingData.builder().firstname(FAKER.name().firstName()).lastname(FAKER.name().lastName())
                .totalprice(FAKER.number().numberBetween(1, 1000)).depositpaid(true)
                .bookingdates(BookingDates.builder()
                        .checkin(formatter.format(FAKER
                                .date().past(5, TimeUnit.DAYS)))
                        .checkout(formatter.format(FAKER.date().future(20, TimeUnit.DAYS))).build())
                 .additionalneeds("Lunch").build();

    }


}
