package com.dimo.auction.domain.auction.vos;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void wouldNotAllowForNullValueAsPrice(){
        assertThrows(NullPointerException.class, () -> Price.of(null));
    }


    @Test
    void wouldNotAllowForNegativeValueAsPrice(){
        assertThrows(IllegalArgumentException.class, () -> Price.of(new BigInteger("-5")));
    }


}