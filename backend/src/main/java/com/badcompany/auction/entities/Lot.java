package com.badcompany.auction.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Lot {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Long price;
    @NotNull
    private Long ownerID;
    private Long bidderID;
    @NotNull
    private boolean isBuyout;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + '\"' +
                ", \"price\":" + price +
                ", \"ownerID\":" + ownerID +
                ", \"bidderID\":" + bidderID +
                ", \"isBuyout\":" + isBuyout +
                '}';
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
        this.price=price;
        this.ownerID=ownerID;
        this.isBuyout=isBuyout;
    }

    public Lot(Long ownerID, Long price, String name, boolean isBuyout){
        this.ownerID=ownerID;
        this.price=price;
        this.isBuyout=isBuyout;
        this.name=name;
    }
}
