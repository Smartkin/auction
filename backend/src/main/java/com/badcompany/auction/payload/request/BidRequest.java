package com.badcompany.auction.payload.request;

import javax.validation.constraints.NotNull;

public class BidRequest {
    @NotNull
    private Long lotId;

    @NotNull
    private Long priceIncrease;

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }

    public Long getPriceIncrease() {
        return priceIncrease;
    }

    public void setPriceIncrease(Long priceIncrease) {
        this.priceIncrease = priceIncrease;
    }
}
