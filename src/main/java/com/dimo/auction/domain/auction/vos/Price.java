package com.dimo.auction.domain.auction.vos;

import com.dimo.auction.domain.shared.ValidationUtil;
import com.dimo.auction.domain.shared.ValueObject;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class Price extends ValueObject implements Comparable<Price>{

    private static final Price ONE = new Price(BigInteger.ONE);
    private static final Price TWO = new Price(BigInteger.TWO);
    private static final Price TEN = new Price(BigInteger.TEN);


    private final BigInteger amount;

    public Price(BigInteger amount){
        this.amount = isValidAmount(amount);
    }

    public static Price one(){
        return ONE;
    }

    public static Price two(){
        return TWO;
    }

    public static Price ten(){
        return TEN;
    }


    private static BigInteger isValidAmount(BigInteger amount) {
        ValidationUtil.getValidator()
                .notNull(amount, "Amount can't be null")
                .isFalse(amount.compareTo(BigInteger.ZERO) < 0, "Price can't be a negative value");
        return amount;
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
