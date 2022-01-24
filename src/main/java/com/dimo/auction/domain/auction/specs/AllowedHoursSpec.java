package com.dimo.auction.domain.auction.specs;

import com.dimo.auction.domain.shared.AbstractSpecification;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AllowedHoursSpec extends AbstractSpecification<LocalDateTime> {


    private static final AllowedHoursSpec instance = new AllowedHoursSpec();

    private final LocalTime allowedStartTime;
    private final LocalTime allowedEndTime;

    private AllowedHoursSpec() {
        allowedStartTime = LocalTime.of(8, 0);
        allowedEndTime = LocalTime.of(18, 0);
    }

    @Override
    public boolean isSatisfiedBy(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        return time.isAfter(allowedStartTime)
                && time.isBefore(allowedEndTime);
    }

    public static AllowedHoursSpec getInstance(){
        return instance;
    }
}
