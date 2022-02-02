package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.exceptions.TimingModificationException;
import com.dimo.auction.domain.auction.specs.AuctionTimingSpec;
import com.dimo.auction.domain.auction.vos.AuctionBids;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.auction.vos.AuctionTiming;
import com.dimo.auction.domain.shared.Id;
import com.dimo.auction.domain.shared.RootEntity;
import com.dimo.auction.domain.shared.ValidationUtil;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Auction extends RootEntity {

    private final Id itemId;
    private final Id accountId;
    private AuctionTiming timing;
    private AuctionBids bids;


    public Auction(Id id, Id itemId, Id accountId, Price basePrice, AuctionTiming timing) {
        this(id, itemId, accountId, timing, AuctionBids.emptyBidsList(basePrice));
    }

    public Auction(Id id, Id itemId, Id accountId, AuctionTiming timing, AuctionBids bids) {
        super(id);
        ValidationUtil.getValidator()
                .notNull(itemId, "ItemId")
                .notNull(accountId, "accountId")
                .notNull(timing, "timing")
                .notNull(bids, "auction bids");
        this.itemId = itemId;
        this.accountId = accountId;
        this.bids = bids;
        this.timing = doesSatisfySpecs(timing);

    }

    public void updateTiming(AuctionTiming timing, LocalDateTime currentDateTime){
        if(!this.timing.isUpdatableTo(timing, currentDateTime))
            throw new TimingModificationException();
        this.timing = doesSatisfySpecs(timing);
    }

    public void bid(Id accountId ,Price price, LocalDateTime currentDateTime){
        bids = bids.bid(accountId, price, timing, currentDateTime);
    }

    private AuctionTiming doesSatisfySpecs(AuctionTiming timing) {
        AuctionTimingSpec.getInstance().check(timing);
        return timing;
    }
}
