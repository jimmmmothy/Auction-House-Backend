package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.ItemManager;
import bg.dimitar.individual.business.custom_exception.NotFoundException;
import bg.dimitar.individual.business.custom_exception.UnauthorizedChangeException;
import bg.dimitar.individual.persistance.ItemRepository;
import bg.dimitar.individual.persistance.entity.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ItemManagerImplTest {
    private ItemManager itemManager;
    private ItemRepository repository;

    @BeforeEach
    public void setUp() {
        repository = mock(ItemRepository.class);
        itemManager = new ItemManagerImpl(repository);
    }

    @Test
    void getItemById_Success() {
        long itemId = 1L;
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);

        when(repository.findById(itemId)).thenReturn(Optional.of(itemEntity));

        ItemEntity result = itemManager.getItemByID(itemId);

        assertEquals(itemId, result.getId());
    }

    @Test
    void getItemById_ReturnsNull_WhenNotFound() {
        long itemId = 1L;

        when(repository.findById(itemId)).thenReturn(Optional.empty());

        ItemEntity result = itemManager.getItemByID(itemId);

        assertNull(result);
    }

    @Test
    void getAllItems_Success() {
        List<ItemEntity> items = new ArrayList<>();
        items.add(new ItemEntity());
        items.add(new ItemEntity());

        when(repository.findAll()).thenReturn(items);

        List<ItemEntity> result = itemManager.getAllItems();

        assertEquals(2, result.size());
    }

    @Test
    void addItem_Success() {
        ItemEntity itemEntity = new ItemEntity();

        when(repository.save(itemEntity)).thenReturn(itemEntity);

        ItemEntity result = itemManager.addItem(itemEntity);

        assertNotNull(result);
    }

    @Test
    void updateItem_ThrowsException_WhenNotFound() {
        long userId = 1L;
        long realItemId = 1L;
        ItemEntity realItemEntity = new ItemEntity();
        realItemEntity.setId(realItemId);
        realItemEntity.setPostedByUserId(1L);

        ItemEntity searchItemEntity = new ItemEntity();

        when(repository.findById(realItemId)).thenReturn(Optional.of(realItemEntity));

        assertThrows(NotFoundException.class, () -> itemManager.updateItem(searchItemEntity, userId, true));
    }

    @Test
    void updateItem_ThrowsException_WhenUnauthorized() {
        long userId = 1L;
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setPostedByUserId(2L);

        when(repository.findById(itemEntity.getId())).thenReturn(Optional.of(itemEntity));

        assertThrows(UnauthorizedChangeException.class, () -> itemManager.updateItem(itemEntity, userId, false));
    }

    @Test
    void updateItem_SavesItem_WhenAuthorized() throws UnauthorizedChangeException, NotFoundException {
        long userId = 1L;
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setPostedByUserId(userId);

        when(repository.findById(itemEntity.getId())).thenReturn(Optional.of(itemEntity));

        boolean result = itemManager.updateItem(itemEntity, userId, false);

        assertTrue(result);
        verify(repository, times(1)).save(itemEntity);
    }

    @Test
    void updateItem_SavesItem_WhenAdmin() throws UnauthorizedChangeException, NotFoundException {
        long userId = 1L;
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(1L);
        itemEntity.setPostedByUserId(2L);

        when(repository.findById(itemEntity.getId())).thenReturn(Optional.of(itemEntity));

        boolean result = itemManager.updateItem(itemEntity, userId, true);

        assertTrue(result);
        verify(repository, times(1)).save(itemEntity);
    }

    @Test
    void deleteItem_ThrowsException_WhenNotFound() {
        long userId = 1L;
        long realItemId = 1L;
        long searchItemId = 2L;
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(realItemId);
        itemEntity.setPostedByUserId(1L);

        when(repository.findById(realItemId)).thenReturn(Optional.of(itemEntity));

        assertThrows(NotFoundException.class, () -> itemManager.deleteItem(searchItemId, userId, false));
    }

    @Test
    void deleteItem_ThrowsException_WhenUnauthorized() {
        long userId = 1L;
        long itemId = 1L;
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);
        itemEntity.setPostedByUserId(2L);

        when(repository.findById(itemId)).thenReturn(Optional.of(itemEntity));

        assertThrows(UnauthorizedChangeException.class, () -> itemManager.deleteItem(itemId, userId, false));
    }

    @Test
    void deleteItem_DeletesItem_WhenAuthorized() throws UnauthorizedChangeException, NotFoundException {
        long userId = 1L;
        long itemId = 1L;
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);
        itemEntity.setPostedByUserId(userId);

        when(repository.findById(itemId)).thenReturn(Optional.of(itemEntity));

        boolean result = itemManager.deleteItem(itemId, userId, false);

        assertTrue(result);
        verify(repository, times(1)).deleteById(itemId);
    }

    @Test
    void deleteItem_DeletesItem_WhenAdmin() throws UnauthorizedChangeException, NotFoundException {
        long userId = 1L;
        long newUserId = 2L;
        long itemId = 1L;
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(itemId);
        itemEntity.setPostedByUserId(userId);

        when(repository.findById(itemId)).thenReturn(Optional.of(itemEntity));

        boolean result = itemManager.deleteItem(itemId, newUserId, true);

        assertTrue(result);
        verify(repository, times(1)).deleteById(itemId);
    }
}