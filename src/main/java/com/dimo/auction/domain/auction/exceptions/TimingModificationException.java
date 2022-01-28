package com.dimo.auction.domain.auction.exceptions;

public class TimingModificationException extends RuntimeException{

    public TimingModificationException() {
        super("can't modify a closed auction timing or live auction start time");
    }
}
