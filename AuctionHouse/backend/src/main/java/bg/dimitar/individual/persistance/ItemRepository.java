package bg.dimitar.individual.persistance;

import bg.dimitar.individual.persistance.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<ItemEntity> getItemByID(long id);
    List<ItemEntity> getAllItems();
    List<ItemEntity> getAllItemsByCategory(String category);
    boolean addItem(ItemEntity item);
    boolean updateItem(ItemEntity item);
    boolean deleteItem(long id);
}
