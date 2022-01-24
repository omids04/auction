package com.dimo.auction.domain.auction.services;

import com.dimo.auction.domain.auction.exceptions.AuctionNotWithinAllowedHoursException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuctionOperationsTest {

    @Test
    void creatingAnAuctionThatStartsAt2AmShouldThrowsError() {
        //given
        LocalDateTime startTime = LocalDateTime.of(2020, 10,5, 2, 10);
        Duration duration = Duration.ofMinutes(50);
        UUID itemId = UUID.randomUUID();
        BigInteger basePrice = BigInteger.TEN;


        //when
        //then
        assertThrows(AuctionNotWithinAllowedHoursException.class,
                () -> AuctionOperations.getInstance().createAuction(itemId, basePrice, startTime, duration));
    }

    @Test
    void creatingAnAuctionThatEndsAt7PMShouldThrowsError() {
        //given
        LocalDateTime startTime = LocalDateTime.of(2020, 10,5, 17, 58);
        Duration duration = Duration.ofMinutes(70);
        UUID itemId = UUID.randomUUID();
        BigInteger basePrice = BigInteger.TEN;


        //when
        //then
        assertThrows(AuctionNotWithinAllowedHoursException.class,
                () -> AuctionOperations.getInstance().createAuction(itemId, basePrice, startTime, duration));
    }
}