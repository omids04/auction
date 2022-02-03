package com.dimo.auction.infrastructure.adaptor.input.servlet.model.res;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.util.UUID;


@Builder
@Getter
public class BidDetailsModel {

    private int id;
    private int rank;
    private UUID accountId;
    private BigInteger price;
}
