package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.BiddingManager;
import bg.dimitar.individual.business.custom_exception.NotFoundException;
import bg.dimitar.individual.business.custom_exception.UnauthorizedChangeException;
import bg.dimitar.individual.persistance.BiddingRepository;
import bg.dimitar.individual.persistance.entity.BidEntity;
import bg.dimitar.individual.persistance.entity.ItemEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BiddingManagerImpl implements BiddingManager {
    private final BiddingRepository biddingRepository;
    private final ItemManagerImpl itemManager;

    @Override
    public List<BidEntity> getTop3Bids(Long itemId) {
        return biddingRepository.findTop3ByItemIdOrderByBidAmountDesc(itemId);
    }

    @Override
    public List<Object[]> getBidsByUser(Long userId) {
        return biddingRepository.findDistinctByBidderId(userId);
    }

    @Override
    public boolean addBid(BidEntity bid) {
        if (Objects.equals(bid.getBidderId(), itemManager.getItemByID(bid.getItemId()).getPostedByUserId())) {
            throw new IllegalArgumentException("Seller cannot bid on their own item!");
        }

        if (bid.getBidAmount() < itemManager.getItemByID(bid.getItemId()).getStartingPrice()) {
            throw new IllegalArgumentException("Bid amount is less than the minimum bid amount!");
        }

        if (biddingRepository.findTop3ByItemIdOrderByBidAmountDesc(bid.getItemId()).size() < 3) {
            biddingRepository.save(bid);
            return true;
        }
        if (bid.getBidAmount() < biddingRepository.findTop3ByItemIdOrderByBidAmountDesc(bid.getItemId()).get(2).getBidAmount()) {
            throw new IllegalArgumentException("Bid amount is less than the third highest bid amount!");
        }

        if (bid.getBidAmount() > itemManager.getItemByID(bid.getItemId()).getCurrentBid()) {
            ItemEntity updatedItem = itemManager.getItemByID(bid.getItemId());
            updatedItem.setCurrentBid(bid.getBidAmount());

            try {
                itemManager.updateItem(updatedItem, updatedItem.getPostedByUserId(), true);
            } catch (UnauthorizedChangeException | NotFoundException e) {
                return false;
            }
        }

        biddingRepository.save(bid);
        return true;
    }
}
