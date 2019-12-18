package com.badcompany.auction.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LotRequest {
    @NotBlank
    @Size(min = 10, max = 50)
    private String name;

    @NotBlank
    @Size(max = 1024)
    private String description;

    @NotBlank
    private Long startPrice;

    @NotBlank
    private Boolean isBuyout;

    public Boolean getBuyout() {
        return isBuyout;
    }

    public void setBuyout(Boolean buyout) {
        isBuyout = buyout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Long startPrice) {
        this.startPrice = startPrice;
    }
}
