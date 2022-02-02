package com.dimo.auction.domain.auction.vos;


import com.dimo.auction.domain.auction.exceptions.TimingModificationException;
import com.dimo.auction.domain.shared.ValidationUtil;
import com.dimo.auction.domain.shared.ValueObject;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
public class AuctionTiming extends ValueObject {
    private final LocalDateTime startTime;
    private final Duration duration;


    public AuctionTiming(LocalDateTime startTime, Duration duration) {
        ValidationUtil.getValidator()
                .notNull(startTime, "Start Time")
                .notNull(duration, "Duration");
        this.startTime = startTime;
        this.duration = duration;
    }

    public boolean isLiveAt(LocalDateTime time){
        ValidationUtil.getValidator()
                .notNull(time, "time");

        return startTime.isBefore(time)
                && finishTime().isAfter(time);
    }

    public boolean isClosedAt(LocalDateTime time){
        ValidationUtil.getValidator()
                .notNull(time, "time");

        return startTime.isBefore(time)
                && finishTime().isBefore(time);
    }


    public LocalDateTime finishTime(){
        return startTime.plus(duration);
    }

    // when an auction is live, extending its duration is possible
    private boolean isExtension(AuctionTiming timing, LocalDateTime currentDateTime){
        return this.isLiveAt(currentDateTime)
                && this.getStartTime().isEqual(timing.getStartTime())
                && this.getDuration().compareTo(timing.getDuration()) < 0;
    }

    public boolean isUpdatableTo(AuctionTiming timing, LocalDateTime currentDateTime){
        ValidationUtil.getValidator()
                .notNull(timing, "timing")
                .notNull(currentDateTime, "currentDateTime");

        return !(isLiveAt(currentDateTime) || isClosedAt(currentDateTime))
                || isExtension(timing, currentDateTime);
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
