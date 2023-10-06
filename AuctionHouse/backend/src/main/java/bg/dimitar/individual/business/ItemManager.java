package bg.dimitar.individual.business;

import bg.dimitar.individual.controller.dtos.Item;
import bg.dimitar.individual.persistance.entity.ItemEntity;

import java.util.List;

public interface ItemManager {
    ItemEntity getItemByID(long id);
    List<ItemEntity> getAllItems();
    boolean addItem(ItemEntity item);
    boolean updateItem(ItemEntity item);
    boolean deleteItem(long id);
}