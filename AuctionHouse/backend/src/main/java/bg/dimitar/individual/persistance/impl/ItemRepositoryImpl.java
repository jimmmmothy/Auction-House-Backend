package bg.dimitar.individual.persistance.impl;

import bg.dimitar.individual.persistance.ItemRepository;
import bg.dimitar.individual.persistance.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final List<ItemEntity> savedItems;

    public ItemRepositoryImpl() {
        savedItems = new ArrayList<ItemEntity>(Arrays.asList(
            new ItemEntity(1L, "ime", "categoriq"),
            new ItemEntity(2L, "imence", "categorijka"))
        );
    }

    public List<ItemEntity> getAllItems() {
        return savedItems;
    }

    public List<ItemEntity> getAllItemsByCategory(String category) {
        return savedItems.stream().filter(i -> i.getCategory().equals(category)).toList();
    }
}
