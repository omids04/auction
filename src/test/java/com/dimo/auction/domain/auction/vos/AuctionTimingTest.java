package com.dimo.auction.domain.auction.vos;

import com.dimo.auction.domain.auction.services.CurrentDateTimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionTimingTest {

    @Mock
    CurrentDateTimeService currentDateTimeService;


    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }

    @Test
    void doesNotAllowNullValuesForStartTime(){

        assertThrows(NullPointerException.class, () -> AuctionTiming.of(currentDateTimeService, null, Duration.ofMinutes(10)));
    }

    @Test
    void doesNotAllowNullValuesForDuration(){
        assertThrows(NullPointerException.class, () -> AuctionTiming.of(currentDateTimeService, LocalDateTime.now(), null));
    }

    @Test
    void itShouldAllowBidingDuringBidingTime() {
        // given
        when(currentDateTimeService.current()).thenReturn(getTomorrowNoonTime());
        var timing = AuctionTiming.of(currentDateTimeService, getTomorrowNoonTime().minusMinutes(5), Duration.ofMinutes(10));

        //when
        var result = timing.isBidingAllowedCurrently();

        //then
        assertTrue(result);
    }

    @Test
    void itShouldNotAllowBidingBeforeBidingTime() {
        // given
        when(currentDateTimeService.current()).thenReturn(getTomorrowNoonTime());
        var timing = AuctionTiming.of(currentDateTimeService, getTomorrowNoonTime().plusMinutes(15), Duration.ofMinutes(10));

        //when
        var result = timing.isBidingAllowedCurrently();

        //then
        assertFalse(result);
    }

    @Test
    void itShouldNotAllowBidingAfterBidingTime() {
        // given
        when(currentDateTimeService.current()).thenReturn(getTomorrowNoonTime());
        var timing = AuctionTiming.of(currentDateTimeService,getTomorrowNoonTime().minusMinutes(50), Duration.ofMinutes(10));

        //when
        var result = timing.isBidingAllowedCurrently();

        //then
        assertFalse(result);
    }

    @Test
    void whenStartTimeIsBeforeNowAndEndTimeIsAfterNowThenAuctionIsLive(){
        //given
        var timing = AuctionTiming
                .of(this::getTomorrowNoonTime, getTomorrowNoonTime().minusMinutes(10), Duration.ofMinutes(30));

        //when
        var isLive = timing.isLive();

        //then
        assertTrue(isLive);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-01-30T11:00", "2022-01-30T13:00"})
    void whenStartTimeIsAfterNowOrEndTimeIsBeforeNowThenAuctionIsNotLive(String startTime){
        //given
        var timing = AuctionTiming
                .of(()-> LocalDateTime.parse("2022-01-30T12:00"), LocalDateTime.parse(startTime), Duration.ofMinutes(30));

        //when
        var isLive = timing.isLive();

        //then
        assertFalse(isLive);
    }

    @Test
    void whenStartTimeAndEndTimeIsBeforeNowThenAuctionIsClosed(){
        //given
        var timing = AuctionTiming
                .of(this::getTomorrowNoonTime, getTomorrowNoonTime().minusMinutes(100), Duration.ofMinutes(30));

        //when
        var isLive = timing.isClosed();

        //then
        assertTrue(isLive);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-01-30T11:50", "2022-01-30T13:00"})
    void whenStartTimeOrFinishTimeIsAfterNowThenAuctionIsNotClosed(String startTime){
        //given
        var timing = AuctionTiming
                .of(()-> LocalDateTime.parse("2022-01-30T12:00"), LocalDateTime.parse(startTime), Duration.ofMinutes(30));

        //when
        var isLive = timing.isClosed();

        //then
        assertFalse(isLive);
    }

    @Test
    void whenStartTimeAndEndTimeIsAfterNowThenAuctionHasNotStartedYet(){
        //given
        var timing = AuctionTiming
                .of(this::getTomorrowNoonTime, getTomorrowNoonTime().plusMinutes(100), Duration.ofMinutes(30));

        //when
        var isLive = timing.hasNotStartedYet();

        //then
        assertTrue(isLive);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-01-30T11:00", "2022-01-30T11:50"})
    void whenStartTimeOrFinishTimeIsBeforeNowThenAuctionHasStartedOrClosed(String startTime){
        //given
        var timing = AuctionTiming
                .of(()-> LocalDateTime.parse("2022-01-30T12:00"), LocalDateTime.parse(startTime), Duration.ofMinutes(30));

        //when
        var isLive = timing.hasNotStartedYet();

        //then
        assertFalse(isLive);
    }
}