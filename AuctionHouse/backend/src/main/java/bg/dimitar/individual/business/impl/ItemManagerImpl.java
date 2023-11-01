package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.ItemManager;
import bg.dimitar.individual.controller.dtos.Item;
import bg.dimitar.individual.persistance.ItemRepository;
import bg.dimitar.individual.persistance.entity.ItemEntity;
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
    public ItemEntity getItemByID(long id) {
        final Optional<ItemEntity> itemOptional = repository.getItemById(id);
        return itemOptional.orElse(null);
    }

    @Override
    public List<ItemEntity> getAllItems() {
        List<ItemEntity> returnItems = new ArrayList<>();

        returnItems.addAll(repository.getAllItems());

        return returnItems;
    }

    @Override
    public boolean addItem(ItemEntity item) {
        return repository.addItem(item);
    }

    @Override
    public boolean updateItem(ItemEntity item) {
        return repository.updateItem(item);
    }

    @Override
    public boolean deleteItem(long id) {
        return repository.deleteItem(id);
    }
}
