package bg.dimitar.individual.controller;

import bg.dimitar.individual.controller.dtos.Bid;
import bg.dimitar.individual.persistance.entity.BidEntity;

import java.util.Date;

public class BidTranslator {
    private BidTranslator() {}
    public static Bid translate(BidEntity from) {
        return Bid.builder()
                .itemId(from.getItemId())
                .userId(from.getUserId())
                .bidAmount(from.getBidAmount())
                .build();
    }

    public static BidEntity translate(Bid from) {
        return BidEntity.builder()
                .itemId(from.getItemId())
                .userId(from.getUserId())
                .bidAmount(from.getBidAmount())
                .bidTime(new Date())
                .build();
    }
}
