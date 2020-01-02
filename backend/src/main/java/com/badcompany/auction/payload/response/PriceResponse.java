package com.badcompany.auction.payload.response;

public class PriceResponse {
    private String newPrice;

    public PriceResponse() {
    }

    public PriceResponse(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }
}
