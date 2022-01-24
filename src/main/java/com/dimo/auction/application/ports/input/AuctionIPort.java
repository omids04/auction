package com.dimo.auction.application.ports.input;

import com.dimo.auction.application.ports.output.AuctionOPort;
import com.dimo.auction.application.usecases.AuctionUseCase;
import com.dimo.auction.application.usecases.model.AuctionCreateModel;
import com.dimo.auction.domain.aggregates.auction.Auction;
import com.dimo.auction.domain.services.AuctionOperations;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuctionIPort implements AuctionUseCase {

    private final AuctionOPort auctionOPort;

    @Override
    public void addAuction(AuctionCreateModel auctionCreateModel) {
        Auction auction = createAuction(auctionCreateModel);
    }

    private Auction createAuction(AuctionCreateModel auctionCreateModel) {
        return AuctionOperations
                    .getInstance()
                    .createAuction(auctionCreateModel.getItemId(),
                            auctionCreateModel.getBasePrice(),
                            auctionCreateModel.getStartTime(),
                            auctionCreateModel.getAuctionDuration());
    }
}
