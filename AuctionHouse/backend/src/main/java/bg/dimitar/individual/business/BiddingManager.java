package bg.dimitar.individual.business;

import bg.dimitar.individual.persistance.entity.BidEntity;

import java.util.List;

public interface BiddingManager {
    List<BidEntity> getTop3Bids(Long itemId);
    List<Object[]> getBidsByUser(Long userId);
    boolean addBid(BidEntity bid);
}
