package com.badcompany.auction.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date startDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonFilter("LotOwnerFilter")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id")
    @JsonFilter("LotBidderFilter")
    private User bidder;

    @NotNull
    private ELotStatus status = ELotStatus.LOT_APPROVED;

    @NotNull
    private Boolean isBuyout;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lot")
    private Set<Image> lotImages = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lot_category", joinColumns = @JoinColumn(name = "lot_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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

    public Boolean getBuyout() {
        return isBuyout;
    }

    public void setBuyout(Boolean buyout) {
        isBuyout = buyout;
    }

    public ELotStatus getStatus() {
        return status;
    }

    public void setStatus(ELotStatus status) {
        this.status = status;
    }

    public Set<Image> getLotImages() {
        return lotImages;
    }

    public void setLotImages(Set<Image> lotImages) {
        this.lotImages = lotImages;
    }

    public Lot() { super(); }

    public Lot(Long id, User owner, Long price, String name, boolean isBuyout){
        this.id=id;
        this.name=name;
        this.description="Простое описание лота.";
        this.price=price;
        this.owner=owner;
        this.isBuyout=isBuyout;
    }

    public Lot(User owner, Long price, String name, boolean isBuyout){
        this.owner=owner;
        this.price=price;
        this.description="Простое описание лота.";
        this.startDate = new Date();
        Date endDate = new Date();
        endDate.setTime(endDate.getTime() + 10000000L * 360L);
        this.endDate = endDate;
        this.isBuyout=isBuyout;
        this.name=name;
    }

    public Lot(User owner, Long price, String name, String description, boolean isBuyout){
        this.owner=owner;
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
