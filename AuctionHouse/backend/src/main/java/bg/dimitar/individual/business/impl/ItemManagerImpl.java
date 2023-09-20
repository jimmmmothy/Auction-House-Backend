package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.ItemManager;
import bg.dimitar.individual.controller.dtos.Item;
import bg.dimitar.individual.persistance.ItemRepository;
import bg.dimitar.individual.persistance.entity.ItemEntity;
import bg.dimitar.individual.persistance.impl.ItemRepositoryImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemManagerImpl implements ItemManager {
    private final ItemRepository repository;

    @Override
    public Item getItemByID(long id) {
        final Optional<ItemEntity> itemOptional = repository.getItemByID(id);
        if (itemOptional.isEmpty()) {
            return null;
        }

        Item returnItem = Item.builder()
                .name(itemOptional.get().getName())
                .category(itemOptional.get().getCategory())
                .build();

        return returnItem;
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> returnItems = new ArrayList<>();

        for (ItemEntity item:repository.getAllItems()) {
            Item temp = Item.builder()
                    .name(item.getName())
                    .category(item.getCategory())
                    .build();

            returnItems.add(temp);
        }

        return returnItems;
    }

    @Override
    public boolean addItem(Item item) {
        ItemEntity addedItem = ItemEntity.builder()
                .name(item.getName())
                .category(item.getCategory())
                .build();

        return repository.addItem(addedItem);
    }

    @Override
    public boolean updateItem(long id, Item item) {
        ItemEntity updatedItem = ItemEntity.builder()
                .id(id)
                .name(item.getName())
                .category(item.getCategory())
                .build();

        return repository.updateItem(updatedItem);
    }

    @Override
    public boolean deleteItem(long id) {
        return repository.deleteItem(id);
    }
}
