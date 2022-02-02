package com.dimo.auction.domain.auction.vos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BidIdTest {

    @Test
    void testForNegative(){
        assertThrows(IllegalArgumentException.class, () -> BidId.of(-5));
    }

    @Test
    void equal(){
        assertEquals(BidId.ONE,BidId.of(1));
    }

    @Test
    void incrementTest(){
        assertEquals(BidId.ONE, BidId.ZERO.increment());
    }

    @Test
    void incrementIsSideEffectFree(){
        BidId id = BidId.ONE;
        id.increment();
        assertEquals(BidId.ONE, id);
    }

}