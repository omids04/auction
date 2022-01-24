package com.dimo.auction.application.ports.input;

import com.dimo.auction.application.ports.output.AuctionCreateOPort;
import com.dimo.auction.application.usecases.AuctionCreateUC;
import com.dimo.auction.application.usecases.model.AuctionCreateModel;
import com.dimo.auction.application.usecases.model.AuctionDetailModel;
import com.dimo.auction.domain.auction.Auction;
import com.dimo.auction.domain.auction.services.AuctionOperations;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuctionCreateIPort implements AuctionCreateUC {

    private final AuctionCreateOPort auctionOPort;

    @Override
    public AuctionDetailModel addAuction(AuctionCreateModel auctionCreateModel) {
        Auction auction = createAuction(auctionCreateModel);
        auctionOPort.save(auction);
        return auctionToDetail(auction);
    }

    private Auction createAuction(AuctionCreateModel auctionCreateModel) {
        return AuctionOperations
                    .getInstance()
                    .createAuction(auctionCreateModel.getItemId(),
                            auctionCreateModel.getBasePrice(),
                            auctionCreateModel.getStartTime(),
                            auctionCreateModel.getAuctionDuration());
    }

    private AuctionDetailModel auctionToDetail(Auction auction){
                return AuctionDetailModel
                        .builder()
                        .startTime(auction.timingDetails().getStartTime())
                        .endTime(auction.timingDetails().getEndTime())
                        .id(auction.id().getId())
                        .itemId(auction.item().getItemId())
                        .basePrice(auction.pricingDetails().getBasePrice())
                        .currentHighestBid(auction.pricingDetails().getCurrentHighestBid())
                        .build();
    }
}
