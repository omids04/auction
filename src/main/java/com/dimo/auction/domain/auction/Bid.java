package com.dimo.auction.domain.auction;

import com.dimo.auction.domain.auction.vos.BidId;
import com.dimo.auction.domain.auction.vos.Price;
import com.dimo.auction.domain.shared.Entity;
import com.dimo.auction.domain.shared.Id;
import com.dimo.auction.domain.shared.ValidationUtil;
import lombok.Getter;

@Getter
public class Bid extends Entity<BidId> {

    private final Id accountId;
    private final Price price;

    public Bid(BidId bidId, Id accountId, Price price) {
        super(bidId);

        ValidationUtil.getValidator()
                        .notNull(accountId, "Account Id can't be null")
                        .notNull(price, "Price can't be null");
        this.accountId = accountId;
        this.price = price;
    }
}
