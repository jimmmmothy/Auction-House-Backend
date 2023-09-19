package bg.dimitar.individual.business;

import bg.dimitar.individual.controller.dtos.Item;

import java.util.List;

public interface ItemManager {
    Item getItemByID(long id);
    List<Item> getAllItems();
    boolean addItem(Item item);
    boolean updateItem(long id, Item item);
    boolean deleteItem(long id);
}