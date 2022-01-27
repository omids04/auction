package com.dimo.auction.domain.auction.operations;

import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;

public class AuctionOperations {
    public static Auction build(Id itemId, Id accountId, Price basePrice, AuctionTiming timing) {
        Id id = Id.generate();
        return new Auction(id, itemId, accountId, basePrice, timing);
    }

    public static void main(String[] args) {
        new Auction(Id.generate(), Id.generate(), Id.generate(), Price.of(BigInteger.ONE),
                AuctionTiming.of(() -> LocalDateTime.now(), LocalDateTime.now(), Duration.ofMinutes(30)));
    }
}
