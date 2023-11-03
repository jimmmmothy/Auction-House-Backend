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
    private ItemRepository repository;

    @Override
    public ItemEntity getItemByID(long id) {
        final Optional<ItemEntity> itemOptional = repository.findById(id);
        return itemOptional.orElse(null);
    }

    @Override
    public List<ItemEntity> getAllItems() {
        List<ItemEntity> returnItems = new ArrayList<>();

        returnItems.addAll(repository.findAll());

        return returnItems;
    }

    @Override
    public ItemEntity addItem(ItemEntity item) {
        return repository.save(item);
    }

//    @Override
//    public boolean updateItem(ItemEntity item) {
//        return repository.updateItem(item);
//    }

    @Override
    public void deleteItem(long id) {
        repository.deleteById(id);
    }
}
