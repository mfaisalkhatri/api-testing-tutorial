package io.github.mfaisalkhatri.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookingDates {
    private String checkin;
    private String checkout;
}
