package com.dimo.auction.domain.aggregates.auction;

import java.util.UUID;

public class ItemVO {

    private UUID itemId;

    public ItemVO(UUID itemId) {
        this.itemId = itemId;
    }

    public UUID getItemId() {
        return itemId;
    }
}
