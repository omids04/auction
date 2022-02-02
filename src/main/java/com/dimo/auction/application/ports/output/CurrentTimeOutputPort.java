package com.dimo.auction.application.ports.output;

import java.time.LocalDateTime;

public interface CurrentTimeOutputPort {

    LocalDateTime currentTime();
}
