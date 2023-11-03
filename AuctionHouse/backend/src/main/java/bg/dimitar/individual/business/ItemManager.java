package bg.dimitar.individual.business;

import bg.dimitar.individual.persistance.entity.ItemEntity;

import java.util.List;

public interface ItemManager {
    ItemEntity getItemByID(long id);
    List<ItemEntity> getAllItems();
    ItemEntity addItem(ItemEntity item);
    void deleteItem(long id);
}