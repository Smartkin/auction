package com.badcompany.auction.payload.request;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class LotRequest {
    @NotBlank
    @Size(min = 10, max = 50)
    private String name;

    @Size(max = 1024)
    private String description;

    @DateTimeFormat
    private Date endDate;

    @NotNull
    private Long startPrice;

    @NotNull
    private Long isBuyout;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
