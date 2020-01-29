package com.badcompany.auction.payload.request;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LotRequest {
    @NotBlank
    @Size(min = 10, max = 50)
    private String name;

    @Size(max = 1024)
    private String description;

    @NotNull
    private Long timeAmount;

    @NotNull
    private Long startPrice;

    @NotNull
    private Long isBuyout;

    public Long getTimeAmount() {
        return timeAmount;
    }

    public void setTimeAmount(Long timeAmount) {
        this.timeAmount = timeAmount;
    }

    public Long getBuyout() {
        return isBuyout;
    }

    public void setBuyout(Long buyout) {
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
