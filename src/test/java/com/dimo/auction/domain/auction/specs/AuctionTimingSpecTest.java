package com.dimo.auction.domain.auction.specs;

import com.dimo.auction.domain.auction.exceptions.AuctionTimingException;
import com.dimo.auction.domain.auction.services.CurrentDateTimeService;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuctionTimingSpecTest {

    @Mock
    CurrentDateTimeService currentDateTimeService;

    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }

    @Test
    void testingACorrectTiming(){

        var timing = AuctionTiming.of(currentDateTimeService, getTomorrowNoonTime(), Duration.ofMinutes(30));
        assertDoesNotThrow(() -> AuctionTimingSpec.getInstance().check(timing));

    }

    @Test
    void aTimingWith3MinutesDuration(){
        var timing = AuctionTiming.of(currentDateTimeService, getTomorrowNoonTime(), Duration.ofMinutes(3));
        assertThrows(AuctionTimingException.class, () -> AuctionTimingSpec.getInstance().check(timing));
    }

    @Test
    void aTimingWithBadStartTime(){
        var timing = AuctionTiming.of(currentDateTimeService, getTomorrowNoonTime().plusHours(7), Duration.ofMinutes(30));

        assertThrows(AuctionTimingException.class, () -> AuctionTimingSpec.getInstance().check(timing));

    }

}