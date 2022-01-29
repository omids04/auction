package com.dimo.auction.domain.auction.operations;

import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;

import java.util.List;
import java.util.function.Predicate;

public class AuctionOperations {
    public static Auction build(Id itemId, Id accountId, Price basePrice, AuctionTiming timing) {
        Id id = Id.generate();
        return new Auction(id, itemId, accountId, basePrice, timing);
    }

    public static Predicate<Auction> liveFilter() {
        return (Auction au) -> au.getTiming().isLive();
    }

    public static Predicate<Auction> nonStartedFilter() {
        return (Auction au) -> au.getTiming().hasNotStartedYet();
    }
}
