package com.dimo.auction.domain.auction.operations;

import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;

public class AuctionOperations {
    public static Auction build(Id itemId, Id accountId, Price basePrice, AuctionTiming timing) {
        Id id = Id.generate();
        return new Auction(id, itemId, accountId, basePrice, timing);
    }
}
