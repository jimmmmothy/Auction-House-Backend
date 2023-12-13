package bg.dimitar.individual.business;

import bg.dimitar.individual.business.custom_exception.NotFoundException;
import bg.dimitar.individual.business.custom_exception.UnauthorizedChangeException;
import bg.dimitar.individual.persistance.entity.ItemEntity;

import java.util.List;

public interface ItemManager {
    ItemEntity getItemByID(Long id);
    List<ItemEntity> getAllItems();
    ItemEntity addItem(ItemEntity item);
    boolean updateItem(ItemEntity item, Long userId, boolean isAdmin) throws UnauthorizedChangeException, NotFoundException;
    boolean deleteItem(Long id, Long userId, boolean isAdmin) throws UnauthorizedChangeException, NotFoundException;
}