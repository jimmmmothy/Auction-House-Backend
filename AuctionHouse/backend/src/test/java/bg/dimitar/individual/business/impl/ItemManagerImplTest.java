package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.controller.dtos.Item;
import bg.dimitar.individual.persistance.ItemRepository;
import bg.dimitar.individual.persistance.entity.ItemEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ItemManagerImplTest {

    @Mock
    private ItemRepository itemRepository;

    private final ItemManagerImpl itemManager;

    public ItemManagerImplTest() {
        MockitoAnnotations.openMocks(this);
        this.itemManager = new ItemManagerImpl(itemRepository);
    }

    @Test
    void getItemByID_existingItem_returnItem() {
        long itemId = 1L;
        ItemEntity itemEntity = new ItemEntity(itemId, "ItemName", "ItemCategory");
        when(itemRepository.getItemByID(itemId)).thenReturn(Optional.of(itemEntity));

        ItemEntity result = itemManager.getItemByID(itemId);

        assertEquals(itemEntity.getName(), result.getName());
        assertEquals(itemEntity.getCategory(), result.getCategory());
    }

    @Test
    void getItemByID_nonexistentItem_returnNull() {
        long itemId = 1L;
        when(itemRepository.getItemByID(itemId)).thenReturn(Optional.empty());

        ItemEntity result = itemManager.getItemByID(itemId);

        assertNull(result);
    }

    @Test
    void getAllItems() {
        List<ItemEntity> itemEntities = new ArrayList<>();
        itemEntities.add(new ItemEntity(1L, "Item1", "Category1"));
        itemEntities.add(new ItemEntity(2L, "Item2", "Category2"));

        when(itemRepository.getAllItems()).thenReturn(itemEntities);

        List<ItemEntity> result = itemManager.getAllItems();

        assertEquals(itemEntities.size(), result.size());
        for (int i = 0; i < itemEntities.size(); i++) {
            assertEquals(itemEntities.get(i).getName(), result.get(i).getName());
            assertEquals(itemEntities.get(i).getCategory(), result.get(i).getCategory());
        }
    }

    void addItem() {
        ItemEntity item = ItemEntity.builder().name("NewItem").category("NewCategory").build();

        when(itemRepository.addItem(Mockito.any(ItemEntity.class))).thenReturn(true);

        assertTrue(itemManager.addItem(item));
    }

    @Test
    void updateItem() {
        long itemId = 1L;
        ItemEntity item = ItemEntity.builder().id(itemId).name("UpdatedItem").category("UpdatedCategory").build();

        when(itemRepository.updateItem(Mockito.any(ItemEntity.class))).thenReturn(true);

        assertTrue(itemManager.updateItem(item));
    }

    @Test
    void updateItem_nonexistentItem_returnFalse() {
        long itemId = 1L;
        ItemEntity item = ItemEntity.builder().id(itemId).name("UpdatedItem").category("UpdatedCategory").build();

        when(itemRepository.updateItem(Mockito.any(ItemEntity.class))).thenReturn(false);

        assertFalse(itemManager.updateItem(item));
    }

    @Test
    void deleteItem() {
        long itemId = 1L;

        when(itemRepository.deleteItem(itemId)).thenReturn(true);

        assertTrue(itemManager.deleteItem(itemId));
    }

    @Test
    void deleteItem_nonexistentItem_returnFalse() {
        long itemId = 1L;

        when(itemRepository.deleteItem(itemId)).thenReturn(false);

        assertFalse(itemManager.deleteItem(itemId));
    }
}