package com.dimo.auction.application.ports.input;

import com.dimo.auction.application.ports.output.AuctionCreateOPort;
import com.dimo.auction.application.usecases.model.AuctionCreateModel;
import com.dimo.auction.application.usecases.model.AuctionDetailModel;
import com.dimo.auction.domain.auction.exceptions.AuctionBasePricingException;
import com.dimo.auction.domain.auction.exceptions.AuctionDurationException;
import com.dimo.auction.domain.auction.exceptions.AuctionTimingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Create Auction UseCase Main Scenario: user can create a new auction by providing
// an itemId, startTime, duration and price.
// item with provided id should exist.
// startTime must be in the future.
// duration should be more than 5 minutes.
// auction can only start within allowed hours of day( between 8 AM and 18 PM).
// price should be positive.
@ExtendWith(MockitoExtension.class)
class AuctionIPortTest {

    @Mock
    AuctionCreateOPort auctionOPort;

    @InjectMocks
    AuctionCreateIPort iPort;

    private LocalDateTime getTomorrowNoonTime(){
        LocalTime midnight = LocalTime.NOON;
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today, midnight).plusDays(1);
    }

    //Scenario: UseCase main scenario
    @Test
    void addingNewAuctionScenario() {
        //given
        LocalDateTime startTime = getTomorrowNoonTime();
        AuctionCreateModel auctionCreateModel = AuctionCreateModel
                .builder()
                .startTime(startTime)
                .auctionDuration(Duration.ofMinutes(30))
                .basePrice(BigInteger.TEN)
                .itemId(UUID.randomUUID())
                .build();
        //when
        AuctionDetailModel auctionDetailModel = iPort.addAuction(auctionCreateModel);
        //then
        assertNotNull(auctionDetailModel.getId());
        verify(auctionOPort, times(1)).save(any());
    }


    // Scenario: some user wants to add an action with timing problem
    @ParameterizedTest(name = "#{index} - Run test with dateTime = {0}")
    @ValueSource(strings = {"2022-01-28T01:36", "2022-01-28T21:36", "2021-01-28T12:36", "2021-01-28T17:36"})
    void auctionDoesNotStartWithinAllowedHoursScenario(String dateTime) {
        //given
        LocalDateTime startTime = LocalDateTime.parse(dateTime);
        AuctionCreateModel auctionCreateModel = AuctionCreateModel
                .builder()
                .startTime(startTime)
                .auctionDuration(Duration.ofMinutes(30))
                .basePrice(BigInteger.TEN)
                .itemId(UUID.randomUUID())
                .build();
        //when
        //then
        assertThrows(AuctionTimingException.class, () -> iPort.addAuction(auctionCreateModel));
    }

    @Test
    void addingAuctionWithLessThanFiveMinutesDurationScenario() {
        //given
        LocalDateTime startTime = getTomorrowNoonTime();
        AuctionCreateModel auctionCreateModel = AuctionCreateModel
                .builder()
                .startTime(startTime)
                .auctionDuration(Duration.ofMinutes(3))
                .basePrice(BigInteger.TEN)
                .itemId(UUID.randomUUID())
                .build();
        //when
        //then
        assertThrows(AuctionDurationException.class, () -> iPort.addAuction(auctionCreateModel));
    }

    @Test
    void AuctionWithNegativePriceScenario(){
        //given
        LocalDateTime startTime = getTomorrowNoonTime();
        AuctionCreateModel auctionCreateModel = AuctionCreateModel
                .builder()
                .startTime(startTime)
                .auctionDuration(Duration.ofMinutes(30))
                .basePrice(BigInteger.ZERO)
                .itemId(UUID.randomUUID())
                .build();
        //when
        //then
        assertThrows(AuctionBasePricingException.class, () -> iPort.addAuction(auctionCreateModel));
    }
}