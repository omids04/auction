package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.exceptions.AuctionClosedException;
import com.dimo.auction.domain.auction.exceptions.BidPriceException;
import com.dimo.auction.domain.auction.specs.AuctionTimingSpec;
import com.dimo.auction.domain.auction.vos.Bid;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.shared.Id;
import com.dimo.auction.domain.shared.RootEntity;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

@Getter
public class Auction extends RootEntity {

    private Id itemId;
    private Id accountId;
    private AuctionTiming timing;
    private List<Bid> bids;
    private Price basePrice;


    public Auction(Id id, Id itemId, Id accountId, Price basePrice, AuctionTiming timing) {
        super(id);
        this.setItemId(itemId);
        this.setAccountId(accountId);
        this.setBasePrice(basePrice);
        this.setTiming(timing);
        bids = new ArrayList<>();
    }

    public Auction(Id id, Id itemId, Id accountId, Price basePrice, AuctionTiming timing, List<Bid> bids) {
        super(id);
        this.setItemId(itemId);
        this.setAccountId(accountId);
        this.setBasePrice(basePrice);
        this.setTiming(timing);
        this.bids = new ArrayList<>(bids);
    }

    public void bid(Bid bid){
        if(!timing.isBidingAllowedCurrently())
            throw new AuctionClosedException();
        if(isThisFirstBid()){
            if(bid.getPrice().compareTo(basePrice) < 0)
                throw new BidPriceException(basePrice, bid.getPrice());
        }else {
            Bid highest = this.getHighestBid();
            if(bid.getPrice().compareTo(highest.getPrice()) <= 0)
                throw new BidPriceException(basePrice, bid.getPrice());
        }
        bids.add(bid);
    }

    private boolean isThisFirstBid(){
        return bids.isEmpty();
    }

    public Optional<Bid> highestBid(){
        if(this.bids.isEmpty())
            return Optional.empty();
        return Optional.of(getHighestBid());
    }

    private Bid getHighestBid(){
        return Collections.max(bids, Comparator.comparing(Bid::getPrice));
    }

    public void updateTiming(AuctionTiming timing){
        this.setTiming(timing);
    }

    private void setItemId(@NonNull Id id){
        this.itemId = id;
    }

    private void setAccountId(@NonNull Id id){
        this.accountId = id;
    }

    private void setBasePrice(@NonNull Price basePrice){this.basePrice = basePrice;}

    private void setTiming(@NonNull AuctionTiming timing){
        AuctionTimingSpec.getInstance().check(timing);
        this.timing = timing;
    }

}
