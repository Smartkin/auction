package com.badcompany.auction.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Lot {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 10, max = 50)
    private String name;
    @NotNull
    @Size(max = 1024)
    private String description;
    @NotNull
    private Long price;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private Long ownerID;
    private Long bidderID;
    @NotNull
    private Boolean isBuyout;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
    }

    public Long getBidderID() {
        return bidderID;
    }

    public void setBidderID(Long bidderID) {
        this.bidderID = bidderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBuyout() {
        return isBuyout;
    }

    public void setBuyout(boolean buyout) {
        isBuyout = buyout;
    }

    public Lot() { super(); }

    public Lot(Long id, Long ownerID, Long price, String name, boolean isBuyout){
        this.id=id;
        this.name=name;
        this.description="Простое описание лота.";
        this.price=price;
        this.ownerID=ownerID;
        this.isBuyout=isBuyout;
    }

    public Lot(Long ownerID, Long price, String name, boolean isBuyout){
        this.ownerID=ownerID;
        this.price=price;
        this.description="Простое описание лота.";
        this.startDate = new Date();
        Date endDate = new Date();
        endDate.setTime(endDate.getTime() + 10000000L * 360L);
        this.endDate = endDate;
        this.isBuyout=isBuyout;
        this.name=name;
    }

    public Lot(Long ownerID, Long price, String name, String description, boolean isBuyout){
        this.ownerID=ownerID;
        this.price=price;
        this.description=description;
        this.startDate = new Date();
        Date endDate = new Date();
        endDate.setTime(endDate.getTime() + 10000000L * 360L);
        this.endDate = endDate;
        this.isBuyout=isBuyout;
        this.name=name;
    }
}
