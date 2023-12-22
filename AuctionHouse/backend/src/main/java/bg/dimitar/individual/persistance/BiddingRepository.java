package bg.dimitar.individual.persistance;

import bg.dimitar.individual.persistance.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BiddingRepository extends JpaRepository<BidEntity, Long> {
    List<BidEntity> findTop3ByItemIdOrderByBidAmountDesc(Long itemId);
    @Query("SELECT DISTINCT b.itemId FROM BidEntity b WHERE b.bidderId = :bidderId")
    List<Integer> findDistinctByBidderId(@Param("bidderId") Long bidderId);
}