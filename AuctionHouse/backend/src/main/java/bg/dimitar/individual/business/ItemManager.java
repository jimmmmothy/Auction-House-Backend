package bg.dimitar.individual.business;

import bg.dimitar.individual.business.custom_exception.UnauthorizedChangeException;
import bg.dimitar.individual.persistance.entity.ItemEntity;

import java.util.List;

public interface ItemManager {
    ItemEntity getItemByID(Long id);
    List<ItemEntity> getAllItems();
    ItemEntity addItem(ItemEntity item);
    boolean updateItem(ItemEntity item, Long userId) throws UnauthorizedChangeException;
    boolean deleteItem(Long id, Long userId) throws UnauthorizedChangeException;
}