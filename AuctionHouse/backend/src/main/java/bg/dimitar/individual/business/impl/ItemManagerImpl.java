package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.ItemManager;
import bg.dimitar.individual.business.custom_exception.UnauthorizedChangeException;
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
    public ItemEntity getItemByID(Long id) {
        final Optional<ItemEntity> itemOptional = repository.findById(id);
        return itemOptional.orElse(null);
    }

    @Override
    public List<ItemEntity> getAllItems() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public ItemEntity addItem(ItemEntity item) {
        return repository.save(item);
    }

    @Override
    public boolean updateItem(ItemEntity item, Long userId) throws UnauthorizedChangeException {
        Optional<ItemEntity> itemFromDb = repository.findById(item.getId());

        if (itemFromDb.isPresent() && !itemFromDb.get().getPostedByUserId().equals(userId)) {
            throw new UnauthorizedChangeException("You do not have permission to edit this item");
        }

        repository.save(item);
        return true;
    }

    @Override
    public boolean deleteItem(Long id, Long userId) throws UnauthorizedChangeException {
        Optional<ItemEntity> itemFromDb = repository.findById(id);

        if (itemFromDb.isPresent() && !itemFromDb.get().getPostedByUserId().equals(userId)) {
            throw new UnauthorizedChangeException("You do not have permission to delete this item");
        }

        repository.deleteById(id);
        return true;
    }
}
