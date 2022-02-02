package com.dimo.auction.domain.auction.vos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuctionTimingTest {



    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }

    @Test
    void doesNotAllowNullValuesForStartTime(){

        assertThrows(NullPointerException.class, () -> new AuctionTiming(null, Duration.ofMinutes(10)));
    }

    @Test
    void doesNotAllowNullValuesForDuration(){
        assertThrows(NullPointerException.class, () -> new AuctionTiming(LocalDateTime.now(), null));
    }

    @Test
    void itShouldAllowBidingDuringBidingTime() {
        // given
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(5), Duration.ofMinutes(10));

        //when
        var result = timing.isLiveAt(getTomorrowNoonTime());

        //then
        assertTrue(result);
    }

    @Test
    void itShouldNotAllowBidingBeforeBidingTime() {
        // given
        var timing = new AuctionTiming(getTomorrowNoonTime().plusMinutes(15), Duration.ofMinutes(10));

        //when
        var result = timing.isLiveAt(getTomorrowNoonTime());

        //then
        assertFalse(result);
    }

    @Test
    void itShouldNotAllowBidingAfterBidingTime() {
        // given
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(50), Duration.ofMinutes(10));

        //when
        var result = timing.isLiveAt(getTomorrowNoonTime());

        //then
        assertFalse(result);
    }

    @Test
    void whenStartTimeIsBeforeNowAndEndTimeIsAfterNowThenAuctionIsLive(){
        //given
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(10), Duration.ofMinutes(30));

        //when
        var isLive = timing.isLiveAt(getTomorrowNoonTime());

        //then
        assertTrue(isLive);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-01-30T11:00", "2022-01-30T13:00"})
    void whenStartTimeIsAfterNowOrEndTimeIsBeforeNowThenAuctionIsNotLive(String startTime){
        //given
        var timing = new AuctionTiming(LocalDateTime.parse(startTime), Duration.ofMinutes(30));

        //when
        var isLive = timing.isLiveAt(LocalDateTime.parse("2022-01-30T12:00"));

        //then
        assertFalse(isLive);
    }

    @Test
    void whenStartTimeAndEndTimeIsBeforeNowThenAuctionIsClosed(){
        //given
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(100), Duration.ofMinutes(30));

        //when
        var isLive = timing.isClosedAt(getTomorrowNoonTime());

        //then
        assertTrue(isLive);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2022-01-30T11:50", "2022-01-30T13:00"})
    void whenStartTimeOrFinishTimeIsAfterNowThenAuctionIsNotClosed(String startTime){
        //given
        var timing = new AuctionTiming(LocalDateTime.parse(startTime), Duration.ofMinutes(30));

        //when
        var isLive = timing.isClosedAt(LocalDateTime.parse("2022-01-30T12:00"));

        //then
        assertFalse(isLive);
    }

    @Test
    void whenAuctionHasNotStartedYetThenTimingShouldBeUpdatable(){
        //given
        var currentTiming = new AuctionTiming(getTomorrowNoonTime().plusDays(1), Duration.ofMinutes(30));
        var newTiming = new AuctionTiming(getTomorrowNoonTime().plusDays(2), Duration.ofMinutes(20));

        //when
        var isUpdatable = currentTiming.isUpdatableTo(newTiming, getTomorrowNoonTime());

        //then
        assertTrue(isUpdatable);
    }

    @Test
    void whenAuctionIsClosedThenTimingShouldNotBeUpdatable(){
        //given
        var currentTiming = new AuctionTiming(getTomorrowNoonTime().minusDays(1), Duration.ofMinutes(30));
        var newTiming = new AuctionTiming(getTomorrowNoonTime().plusDays(2), Duration.ofMinutes(20));

        //when
        var isUpdatable = currentTiming.isUpdatableTo(newTiming, getTomorrowNoonTime());

        //then
        assertFalse(isUpdatable);
    }

    @Test
    void whenAuctionIsLiveDurationShouldBeExpandable(){
        //given
        var startTime = getTomorrowNoonTime().minusMinutes(10);
        var currentTiming = new AuctionTiming(startTime, Duration.ofMinutes(30));
        var newTiming = new AuctionTiming(startTime, Duration.ofMinutes(35));

        //when
        var isUpdatable = currentTiming.isUpdatableTo(newTiming, getTomorrowNoonTime());

        //then
        assertTrue(isUpdatable);
    }

    @Test
    void whenAuctionIsLiveDurationShouldNotBeReducible(){
        //given
        var startTime = getTomorrowNoonTime().minusMinutes(10);
        var currentTiming = new AuctionTiming(startTime, Duration.ofMinutes(30));
        var newTiming = new AuctionTiming(startTime, Duration.ofMinutes(25));

        //when
        var isUpdatable = currentTiming.isUpdatableTo(newTiming, getTomorrowNoonTime());

        //then
        assertFalse(isUpdatable);
    }

    @Test
    void whenAuctionIsLiveThenStartTimeShouldNotBeUpdatable(){
        //given
        var currentTiming = new AuctionTiming(getTomorrowNoonTime().minusMinutes(2), Duration.ofMinutes(20));
        var newTiming = new AuctionTiming(getTomorrowNoonTime().plusDays(1), Duration.ofMinutes(20));

        //when
        var isUpdatable = currentTiming.isUpdatableTo(newTiming, getTomorrowNoonTime());

        //then
        assertFalse(isUpdatable);
    }
}