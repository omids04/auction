package com.dimo.auction.application.ports.input;

import com.dimo.auction.application.ports.output.AuctionOutputPort;
import com.dimo.auction.application.usecases.commands.AuctionCreateUC;

import com.dimo.auction.application.usecases.commands.BidingUC;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.operations.AuctionOperations;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.auction.vos.Bid;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Id;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuctionInputPort implements AuctionCreateUC, BidingUC {

    private final AuctionOutputPort auctionOP;

    @Override
    public Auction create(Id itemId, Id accountId, Price basePrice,AuctionTiming timing) {
        Auction auction = AuctionOperations.build(itemId, accountId, basePrice, timing);
        auctionOP.save(auction);
        return auction;
    }

    @Override
    public void placeBid(Id auctionId, Id accountId, Price price) {
        Auction auction = auctionOP.getById(auctionId);
        Bid bid = new Bid(accountId, price);
        auction.bid(bid);
        auctionOP.save(auction);
    }
}
