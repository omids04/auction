package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.exceptions.BidPriceException;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AuctionTest {


    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1" ,"5"})
    void placingFirstBid(String price) {
        //given
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(20), Duration.ofMinutes(40));
        var auction = new Auction(Id.generate(), Id.generate(), Id.generate(), Price.one(), timing);

        //when
        auction.bid(Id.generate(), new Price(new BigInteger(price)), getTomorrowNoonTime());

        //then
        assertEquals(new Price(new BigInteger(price)) , auction.getBids().highestBid().orElse(null).getPrice());
    }

    @Test
    void firstBidShouldNotBeLowerThanBasePrice() {
        //given
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(20), Duration.ofMinutes(40));
        var auction = new Auction(Id.generate(), Id.generate(), Id.generate(), new Price(BigInteger.TEN), timing);

        //when
        //then
        assertThrows(BidPriceException.class, () -> auction.bid(Id.generate(), Price.one(), getTomorrowNoonTime()));
    }



}