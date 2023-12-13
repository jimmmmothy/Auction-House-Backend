package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.ItemManager;
import bg.dimitar.individual.business.custom_exception.NotFoundException;
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
    public boolean updateItem(ItemEntity item, Long userId, boolean isAdmin) throws UnauthorizedChangeException, NotFoundException {
        Optional<ItemEntity> itemFromDb = repository.findById(item.getId());

        if (itemFromDb.isEmpty()) {
            throw new NotFoundException("Item not found");
        }
        if (!itemFromDb.get().getPostedByUserId().equals(userId) && !isAdmin) {
            throw new UnauthorizedChangeException("You do not have permission to edit this item");
        }

        repository.save(item);
        return true;
    }

    @Override
    public boolean deleteItem(Long id, Long userId, boolean isAdmin) throws UnauthorizedChangeException, NotFoundException {
        Optional<ItemEntity> itemFromDb = repository.findById(id);

        if (itemFromDb.isEmpty()) {
            throw new NotFoundException("Item not found");
        }

        if (!itemFromDb.get().getPostedByUserId().equals(userId) && !isAdmin) {
            throw new UnauthorizedChangeException("You do not have permission to delete this item");
        }

        repository.deleteById(id);
        return true;
    }
}
