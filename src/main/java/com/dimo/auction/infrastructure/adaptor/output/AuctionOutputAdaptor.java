package com.dimo.auction.infrastructure.adaptor.output;

import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.Bid;
import com.dimo.auction.domain.auction.vos.AuctionBids;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.BidId;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AuctionOutputAdaptor implements AuctionOutputPort {

    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }

    @Override
    public void save(Auction auction) {
        System.out.println("auction with id " + auction.getId() + " saved");
    }

    @Override
    public Auction getById(Id id) {
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(5), Duration.ofMinutes(30));
        var bid = new Bid(BidId.ONE, Id.generate(), Price.one());
        var bids = AuctionBids.withBids(List.of(bid), BidId.ONE, Price.one());
        return new Auction(id, Id.generate(), Id.generate(), timing, bids);
    }

    @Override
    public List<Auction> getAll() {
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(5), Duration.ofMinutes(30));
        var bid = new Bid(BidId.ONE, Id.generate(), Price.ten());
        var bids = AuctionBids.withBids(List.of(bid), BidId.ONE, Price.one());
        return List.of(new Auction(Id.generate(), Id.generate(), Id.generate(), timing, bids));
    }

    @Override
    public List<Auction> getByAccountId(Id userId) {
        var timing = new AuctionTiming(getTomorrowNoonTime().minusMinutes(5), Duration.ofMinutes(30));
        var bid = new Bid(BidId.ONE, Id.generate(), Price.one());
        var bids = AuctionBids.withBids(List.of(bid), BidId.ONE, Price.one());
        return List.of(new Auction(Id.generate(), Id.generate(), userId, timing, bids));
    }
}
