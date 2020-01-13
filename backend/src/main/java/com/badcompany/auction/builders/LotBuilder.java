package com.badcompany.auction.builders;

import com.badcompany.auction.entities.Lot;
import com.badcompany.auction.payload.response.LotResponse;
import com.badcompany.auction.repositories.UserRepository;

// Utility class to copy data from lot into POJO LotResponse
public class LotBuilder {

    static public LotResponse build(Lot toBuildFrom, UserRepository repository) {
        LotResponse newLot = new LotResponse();
        newLot.setEndDate(toBuildFrom.getEndDate());
        newLot.setStartDate(toBuildFrom.getStartDate());
        newLot.setBuyout(toBuildFrom.isBuyout());
        newLot.setDescription(toBuildFrom.getDescription());
        newLot.setId(toBuildFrom.getId());
        newLot.setName(toBuildFrom.getName());
        newLot.setPrice(String.valueOf(toBuildFrom.getPrice()));
        newLot.setBidder("");
        if (toBuildFrom.getBidderID() != null) {
            newLot.setBidder(repository.getOne(toBuildFrom.getBidderID()).getUsername());
        }
        newLot.setOwner(repository.getOne(toBuildFrom.getOwnerID()).getUsername());

        return newLot;
    }
}
