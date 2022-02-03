package com.dimo.auction.infrastructure.adaptor.input.servlet.model.req;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class TimingUpdateModel {

    private LocalDateTime startTime;
    private long duration;
}
