package bg.dimitar.individual.persistance;

import bg.dimitar.individual.persistance.entity.ItemEntity;

import java.util.List;

public interface ItemRepository {
    List<ItemEntity> getAllItems();
    List<ItemEntity> getAllItemsByCategory(String category);
}
