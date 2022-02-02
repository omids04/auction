package com.dimo.auction.domain.auction.vos;

import com.dimo.auction.domain.auction.Bid;
import com.dimo.auction.domain.auction.exceptions.AuctionStatusException;
import com.dimo.auction.domain.auction.exceptions.BidPriceException;
import com.dimo.auction.domain.shared.Id;
import com.dimo.auction.domain.shared.ValidationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AuctionBids {
    private final List<Bid> bids;
    private final BidId highestIdAssigned;
    private final Price basePrice;

    public static AuctionBids emptyBidsList(Price basePrice){
        return new AuctionBids(basePrice);
    }

    public static AuctionBids withBids(final List<Bid> bids, final BidId highestIdAssigned,final Price basePrice){
        return new AuctionBids(bids, highestIdAssigned, basePrice);
    }

    private AuctionBids(Price basePrice) {
        ValidationUtil.getValidator()
                .notNull(basePrice, "BasePrice");
        this.bids = new ArrayList<>();
        this.highestIdAssigned = BidId.ZERO;
        this.basePrice = basePrice;
    }

    private AuctionBids(List<Bid> bids, BidId highestIdAssigned, Price basePrice) {
        ValidationUtil.getValidator()
                .notNull(bids, "Bids")
                .notNull(highestIdAssigned, "Highest assigned bid")
                .notNull(basePrice, "BasePrice")
                .isTrue(bids.size() > 0, "Bids list can't be empty. Maybe call forExistingAuction factory method")
                .isTrueState(lastBidIdCheck(bids, highestIdAssigned), "last bid id should be equal to highest Id Assigned")
                .isTrueState(bids.get(0).getPrice().compareTo(basePrice) >= 0, "lowest bid price should be higher than base price")
                .isTrueState(checkBidsPrice(bids), "bids price should be ascending in order of their ids");

        this.bids = bids;
        this.highestIdAssigned = highestIdAssigned;
        this.basePrice = basePrice;
    }

    private boolean checkBidsPrice(List<Bid> bids) {
        for (int i = 0; i < bids.size() - 1 ; i++){
            if(bids.get(i).getPrice().compareTo(bids.get(i+1).getPrice()) >= 0)
                return false;
        }
        return true;
    }

    private boolean lastBidIdCheck(List<Bid> bids, BidId highestIdAssigned) {
        return bids.get(bids.size() - 1).getId().equals(highestIdAssigned);
    }


    public AuctionBids bid(Id accountId, Price price, AuctionTiming timing, LocalDateTime currentDateTime){
        ValidationUtil.getValidator()
                .notNull(accountId, "account Id")
                .notNull(price, "Price")
                .notNull(timing, "timing");

        if(!timing.isLiveAt(currentDateTime))
            throw new AuctionStatusException();
        if(isThisFirstBid()){
            if(price.compareTo(this.basePrice) < 0)
                throw new BidPriceException(basePrice, price);
        }else {
            Bid highest = this.getHighestBid();
            if(price.compareTo(highest.getPrice()) <= 0)
                throw new BidPriceException(basePrice, price);
        }
        List<Bid> newBids = new ArrayList<>(this.bids);
        BidId id = highestIdAssigned.increment();
        newBids.add(new Bid(id, accountId, price));
        return new AuctionBids(newBids, id, this.basePrice);
    }

    private boolean isThisFirstBid(){
        return highestIdAssigned.equals(BidId.ZERO);
    }

    private Bid getHighestBid(){
        return bids.get(bids.size() - 1);
    }

    public List<Bid> bids(){
        return Collections.unmodifiableList(this.bids);
    }

    public BidId highestIdAssigned(){
        return this.highestIdAssigned;
    }

    public Price basePrice(){
        return this.basePrice;
    }

    public Optional<Bid> highestBid() {
        if(highestIdAssigned.equals(BidId.ZERO))
            return Optional.empty();
        return Optional.of(bids.get(bids.size() - 1));
    }
}
