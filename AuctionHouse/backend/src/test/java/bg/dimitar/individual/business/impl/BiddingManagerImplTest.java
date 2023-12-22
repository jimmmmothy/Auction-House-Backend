package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.BiddingManager;
import bg.dimitar.individual.persistance.BiddingRepository;
import bg.dimitar.individual.persistance.entity.BidEntity;
import bg.dimitar.individual.persistance.entity.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BiddingManagerImplTest {
    private BiddingManager biddingManager;
    private BiddingRepository repository;
    private ItemManagerImpl itemManager;

    @BeforeEach
    public void SetUp() {
        repository = mock(BiddingRepository.class);
        itemManager = mock(ItemManagerImpl.class);
        biddingManager = new BiddingManagerImpl(repository, itemManager);
    }

    @Test
    void getTop3Bids(){
        List<BidEntity> bids = new ArrayList<>();
        bids.add(new BidEntity());
        bids.add(new BidEntity());
        bids.add(new BidEntity());

        when(repository.findTop3ByItemIdOrderByBidAmountDesc(1L)).thenReturn(bids);

        List<BidEntity> result = biddingManager.getTop3Bids(1L);

        assertEquals(3, result.size());
    }

    @Test
    void addBid_Success() {
        BidEntity bid = new BidEntity();
        bid.setItemId(1L);
        bid.setBidAmount(11.0);

        ItemEntity itemEntity = mock(ItemEntity.class);
        when(itemManager.getItemByID(1L)).thenReturn(itemEntity);
        when(itemEntity.getStartingPrice()).thenReturn(10.0);

        List<BidEntity> bids = new ArrayList<>();
        bids.add(new BidEntity());
        bids.get(0).setBidAmount(10.0);
        bids.add(new BidEntity());
        bids.get(1).setBidAmount(9.0);
        bids.add(new BidEntity());
        bids.get(2).setBidAmount(8.0);
        when(repository.findTop3ByItemIdOrderByBidAmountDesc(1L)).thenReturn(bids);

        boolean result = biddingManager.addBid(bid);

        assertTrue(result);
        verify(repository).save(any(BidEntity.class));
    }

    @Test
    void addBid_ThrowsException_WhenBidAmountIsLessThanMinimumBidAmount() {
        BidEntity bid = new BidEntity();
        bid.setItemId(1L);
        bid.setBidAmount(9.0);

        ItemEntity itemEntity = mock(ItemEntity.class);
        when(itemManager.getItemByID(1L)).thenReturn(itemEntity);
        when(itemEntity.getStartingPrice()).thenReturn(10.0);

        assertThrows(IllegalArgumentException.class, () -> biddingManager.addBid(bid));
    }

    @Test
    void addBid_ThrowsException_WhenBidAmountIsLessThanThirdHighestBidAmount() {
        BidEntity bid = new BidEntity();
        bid.setItemId(1L);
        bid.setBidAmount(11.0);

        ItemEntity itemEntity = mock(ItemEntity.class);
        when(itemManager.getItemByID(1L)).thenReturn(itemEntity);
        when(itemEntity.getStartingPrice()).thenReturn(10.0);

        List<BidEntity> bids = new ArrayList<>();
        bids.add(new BidEntity());
        bids.get(0).setBidAmount(14.0);
        bids.add(new BidEntity());
        bids.get(1).setBidAmount(13.0);
        bids.add(new BidEntity());
        bids.get(2).setBidAmount(12.0);
        when(repository.findTop3ByItemIdOrderByBidAmountDesc(1L)).thenReturn(bids);

        assertThrows(IllegalArgumentException.class, () -> biddingManager.addBid(bid));
    }

    @Test
    void addBid_ThrowsException_WhenSellerBidsOnTheirOwnItem() {
        BidEntity bid = new BidEntity();
        bid.setItemId(1L);
        bid.setBidAmount(11.0);
        bid.setBidderId(1L);

        ItemEntity itemEntity = mock(ItemEntity.class);
        when(itemManager.getItemByID(1L)).thenReturn(itemEntity);
        when(itemEntity.getStartingPrice()).thenReturn(10.0);
        when(itemEntity.getPostedByUserId()).thenReturn(1L);

        List<BidEntity> bids = new ArrayList<>();
        bids.add(new BidEntity());
        bids.get(0).setBidAmount(10.0);
        bids.add(new BidEntity());
        bids.get(1).setBidAmount(9.0);
        bids.add(new BidEntity());
        bids.get(2).setBidAmount(8.0);
        when(repository.findTop3ByItemIdOrderByBidAmountDesc(1L)).thenReturn(bids);

        assertThrows(IllegalArgumentException.class, () -> biddingManager.addBid(bid));
    }
}