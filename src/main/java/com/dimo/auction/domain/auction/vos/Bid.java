package com.dimo.auction.domain.auction.vos;

import com.dimo.auction.domain.shared.Id;
import com.dimo.auction.domain.shared.ValueObject;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Bid extends ValueObject {
    private Id accountId;
    private Price price;

    public Bid(Id accountId, Price price) {
        setAccountId(accountId);
        setPrice(price);
    }

    private void setAccountId(@NonNull Id id){
        this.accountId = id;
    }
    private void setPrice(@NonNull Price price){
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        if (!accountId.equals(bid.accountId)) return false;
        return price.equals(bid.price);
    }

    @Override
    public int hashCode() {
        int result = accountId.hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
