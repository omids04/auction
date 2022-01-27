package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.exceptions.AuctionClosedException;
import com.dimo.auction.domain.auction.exceptions.BidPriceException;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Bid;
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
import java.util.List;

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
        var timing = AuctionTiming.of(
                this::getTomorrowNoonTime,
                getTomorrowNoonTime().minusMinutes(20),
                Duration.ofMinutes(40));
        var auction = new Auction(Id.generate(), Id.generate(), Id.generate(), Price.of(BigInteger.ONE), timing);
        var bid = new Bid(Id.generate(), Price.of(new BigInteger(price)));

        //when
        auction.bid(bid);

        //then
        assertEquals(bid , auction.highestBid().orElse(null));
    }

    @Test
    void firstBidShouldNotBeLowerThanBasePrice() {
        //given
        var timing = AuctionTiming.of(
                this::getTomorrowNoonTime,
                getTomorrowNoonTime().minusMinutes(20),
                Duration.ofMinutes(40));
        var auction = new Auction(Id.generate(), Id.generate(), Id.generate(), Price.of(BigInteger.TEN), timing);
        var bid = new Bid(Id.generate(), Price.of(BigInteger.ONE));

        //when
        //then
        assertThrows(BidPriceException.class, () -> auction.bid(bid));
    }


    @Test
    void placingANewBid() {
        //given
        var timing = AuctionTiming.of(
                this::getTomorrowNoonTime,
                getTomorrowNoonTime().minusMinutes(20),
                Duration.ofMinutes(40));
        var currentHighest = new Bid(Id.generate(), Price.of(BigInteger.ONE));
        var auction = new Auction(Id.generate(), Id.generate(), Id.generate(), Price.of(BigInteger.TEN), timing, List.of(currentHighest));
        var bid = new Bid(Id.generate(), Price.of(BigInteger.TEN));

        //when
        auction.bid(bid);
        //then
        assertEquals(bid, auction.highestBid().orElse(null));
    }

    @Test
    void shouldThrowExceptionWhenBidingOnClosedAuction() {
        //given
        var timing = AuctionTiming.of(
                this::getTomorrowNoonTime,
                getTomorrowNoonTime().minusMinutes(50),
                Duration.ofMinutes(20));
        var auction = new Auction(Id.generate(), Id.generate(), Id.generate(), Price.of(BigInteger.ONE), timing);
        var bid = new Bid(Id.generate(), Price.of(BigInteger.TEN));

        //when
        //then
        assertThrows(AuctionClosedException.class, () -> auction.bid(bid));
    }


}