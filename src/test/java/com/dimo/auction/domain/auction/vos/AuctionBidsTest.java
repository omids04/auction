package com.dimo.auction.domain.auction.vos;

import com.dimo.auction.domain.auction.Bid;
import com.dimo.auction.domain.auction.exceptions.AuctionStatusException;
import com.dimo.auction.domain.shared.Id;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuctionBidsTest {

    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }

    @Test
    void forNewAuctionBasePriceCantBeNull(){
        assertThrows(NullPointerException.class, () -> AuctionBids.emptyBidsList(null));
    }

    @Test
    void forNewAuctionHighestAssignedIdIsZero(){
        var bids = AuctionBids.emptyBidsList(Price.one());
        assertEquals(BidId.ZERO, bids.highestIdAssigned());
    }

    @Test
    void forNewAuctionBidsListIsEmpty(){
        var bids = AuctionBids.emptyBidsList(Price.one());
        assertEquals(0, bids.bids().size());
    }

    @Test
    void withBidsListCantBeEmpty(){
        assertThrows(IllegalArgumentException.class, () -> AuctionBids.withBids(List.of(), BidId.ZERO, Price.one()));
    }

    @Test
    void withBidsListShouldBeOrdered(){
        var bid1 = new Bid(BidId.ONE, Id.generate(), Price.one());
        var bid2 = new Bid(BidId.ONE.increment(), Id.generate(), Price.ten());

        assertThrows(IllegalArgumentException.class, () -> AuctionBids.withBids(List.of(bid2, bid1), BidId.ONE.increment(), Price.one()));
    }

    @Test
    void withBidsHighestPriceShouldBeLastBidsPrice(){
        //given
        var bid1 = new Bid(BidId.ONE, Id.generate(), Price.one());
        var bid2 = new Bid(BidId.ONE.increment(), Id.generate(), Price.ten());

        //when
        var bids = AuctionBids.withBids(List.of(bid1, bid2), BidId.ONE.increment(), Price.one());

        //then
        assertEquals(bid2.getPrice(), bids.highestBid().get().getPrice());
    }

    @Test
    void thirdBidIdIs3(){
        //given
        var bid1 = new Bid(BidId.ONE, Id.generate(), Price.one());
        var bid2 = new Bid(BidId.ONE.increment(), Id.generate(), Price.ten());
        var bids = AuctionBids.withBids(List.of(bid1, bid2), BidId.ONE.increment(), Price.one());

        //when
        bids = bids.bid(Id.generate(), new Price(new BigInteger("15")),
                new AuctionTiming(getTomorrowNoonTime().minusMinutes(5), Duration.ofMinutes(20)),
                getTomorrowNoonTime());

        //then
        assertEquals(BidId.of(3), bids.highestBid().get().getId());
    }

    @Test
    void bidingIsPossibleWhenAuctionIsLive(){
        //given
        var bids = AuctionBids.emptyBidsList(Price.one());
        var updatableTiming = new AuctionTiming(getTomorrowNoonTime().minusMinutes(5), Duration.ofMinutes(15));

        //when
        var updatedBids = bids.bid(Id.generate(), Price.ten(), updatableTiming, getTomorrowNoonTime());

        //then
        assertEquals(Price.ten(), updatedBids.highestBid().get().getPrice());
    }

    @Test
    void bidingIsNotPossibleWhenAuctionIsClosed(){
        //given
        var bids = AuctionBids.emptyBidsList(Price.one());
        var updatableTiming = new AuctionTiming(getTomorrowNoonTime().minusMinutes(50), Duration.ofMinutes(15));

        //when
        assertThrows(AuctionStatusException.class,() -> bids.bid(Id.generate(), Price.ten(), updatableTiming, getTomorrowNoonTime()));
    }

    @Test
    void bidingIsNotPossibleWhenAuctionHasNotStartedYet(){
        //given
        var bids = AuctionBids.emptyBidsList(Price.one());
        var updatableTiming = new AuctionTiming(getTomorrowNoonTime().plusMinutes(50), Duration.ofMinutes(15));

        //when
        assertThrows(AuctionStatusException.class,() -> bids.bid(Id.generate(), Price.ten(), updatableTiming, getTomorrowNoonTime()));
    }


}