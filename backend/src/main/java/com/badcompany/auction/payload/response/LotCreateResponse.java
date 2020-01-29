package com.badcompany.auction.payload.response;

public class LotCreateResponse {
    private String message;
    private Long lotId;

    public LotCreateResponse(String message, Long lotId) {
        this.message = message;
        this.lotId = lotId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLotId() {
        return lotId;
    }

    public void setLotId(Long lotId) {
        this.lotId = lotId;
    }
}
