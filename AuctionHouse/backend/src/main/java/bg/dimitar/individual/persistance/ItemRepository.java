package bg.dimitar.individual.persistance;

import bg.dimitar.individual.persistance.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    Optional<ItemEntity> getItemById(long id);
    List<ItemEntity> getAllItems();
    List<ItemEntity> getAllItemsByCategory(String category);
    boolean addItem(ItemEntity item);
    boolean updateItem(ItemEntity item);
    boolean updateCurrentBidById(long id, double currentBid);
    boolean deleteItem(long id);
}
