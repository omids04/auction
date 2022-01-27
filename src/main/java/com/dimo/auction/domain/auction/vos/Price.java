package com.dimo.auction.domain.auction.vos;

import com.dimo.auction.domain.shared.ValueObject;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigInteger;

@Getter
public class Price extends ValueObject implements Comparable<Price>{

    private BigInteger amount;

    private Price(){}

    public static Price of(BigInteger amount){
        Price price = new Price();
        price.setAmount(amount);
        return price;
    }


    private void setAmount(@NonNull BigInteger amount) {
        if(amount.compareTo(BigInteger.ZERO) < 0)
            throw new IllegalArgumentException("price can't be a negative value");
        this.amount = amount;
    }

    @Override
    public int compareTo(Price o) {
        return this.amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        return amount.equals(price.amount);
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }
}
