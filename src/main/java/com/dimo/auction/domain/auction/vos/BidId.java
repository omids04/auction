package com.dimo.auction.domain.auction.vos;

import com.dimo.auction.domain.shared.ValidationUtil;
import com.dimo.auction.domain.shared.ValueObject;
import lombok.Getter;

@Getter
public class BidId extends ValueObject {

    public static final BidId ZERO= new BidId(0);
    public static final BidId ONE= new BidId(1);

    private final int id;

    private BidId(int id){
        this.id = id;
    }

    public static BidId of(int id){
        ValidationUtil.getValidator()
                .isTrue(id >= 0, "Id can't be a negative value");
        if(id == 0)
            return ZERO;
        if (id == 1)
            return ONE;
        return new BidId(id);
    }

    public BidId increment(){
        return of(this.id + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BidId bidId = (BidId) o;

        return id == bidId.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
