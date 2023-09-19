package bg.dimitar.individual.persistance.impl;

import bg.dimitar.individual.persistance.ItemRepository;
import bg.dimitar.individual.persistance.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private static long NEXT_ID;
    private final List<ItemEntity> savedItems;

    public Optional<ItemEntity> getItemByID(long id) {
        return savedItems.stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public ItemRepositoryImpl() {
        savedItems = new ArrayList<ItemEntity>(Arrays.asList(
            new ItemEntity(1L, "ime", "categoriq"),
            new ItemEntity(2L, "imence", "categorijka"))
        );

        NEXT_ID = savedItems.size() + 1;
    }

    @Override
    public List<ItemEntity> getAllItems() {
        return savedItems;
    }

    @Override
    public List<ItemEntity> getAllItemsByCategory(String category) {
        return savedItems.stream().filter(i -> i.getCategory().equals(category)).toList();
    }

    @Override
    public boolean addItem(ItemEntity item) {
        item.setId(NEXT_ID);
        NEXT_ID++;

        return savedItems.add(item);
    }

    @Override
    public boolean updateItem(ItemEntity item) {
        for (int i = 0; i < savedItems.size(); i++) {
            if (savedItems.get(i).getId().equals(item.getId())) {
                savedItems.set(i, item);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteItem(long id) {
        Optional<ItemEntity> temp = savedItems.stream().filter(item -> item.getId() == id).findFirst();
        return savedItems.remove(temp.get());
    }
}
