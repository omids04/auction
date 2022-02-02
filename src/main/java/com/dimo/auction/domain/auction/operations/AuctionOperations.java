package com.dimo.auction.domain.auction.operations;

import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class AuctionOperations {
    public static Auction build(Id itemId, Id accountId, Price basePrice, AuctionTiming timing) {
        Id id = Id.generate();
        return new Auction(id, itemId, accountId, basePrice, timing);
    }

    public static Predicate<Auction> liveFilter(LocalDateTime current) {
        return (Auction au) -> au.getTiming().isLiveAt(current);
    }

    public static Predicate<Auction> nonStartedFilter(LocalDateTime current) {
        return (Auction au) -> !(au.getTiming().isLiveAt(current) || au.getTiming().isClosedAt(current));
    }
}
