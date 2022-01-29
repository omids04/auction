package com.dimo.auction.domain.auction.vos;


import com.dimo.auction.domain.auction.services.CurrentDateTimeService;
import com.dimo.auction.domain.shared.ValueObject;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class AuctionTiming extends ValueObject {
    private LocalDateTime startTime;
    private Duration duration;
    private CurrentDateTimeService currentDateTimeService;

    private AuctionTiming(){}

    public static AuctionTiming of(CurrentDateTimeService currentDateTimeService, LocalDateTime startTime, Duration duration){
        AuctionTiming timing = new AuctionTiming();
        timing.currentDateTimeService = currentDateTimeService;
        timing.setDuration(duration);
        timing.setStartTime(startTime);
        return timing;
    }

    public boolean isBidingAllowedCurrently(){
        var now = currentDateTimeService.current();
        return startTime.isBefore(now)
                && startTime.plus(duration).isAfter(now);
    }

    public boolean isLive(){
        return this.isBidingAllowedCurrently();
    }

    public boolean isClosed(){
        var now = currentDateTimeService.current();
        return startTime.isBefore(now)
                && startTime.plus(duration).isBefore(now);
    }

    public boolean hasNotStartedYet(){
        var now = currentDateTimeService.current();
        return startTime.isAfter(now)
                && startTime.plus(duration).isAfter(now);
    }

    private void setStartTime(@NonNull LocalDateTime startTime){
        this.startTime = startTime;
    }

    private void setDuration(@NonNull Duration duration){
        this.duration = duration;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionTiming that = (AuctionTiming) o;

        if (!startTime.equals(that.startTime)) return false;
        return duration.equals(that.duration);
    }

    @Override
    public int hashCode() {
        int result = startTime.hashCode();
        result = 31 * result + duration.hashCode();
        return result;
    }
}
