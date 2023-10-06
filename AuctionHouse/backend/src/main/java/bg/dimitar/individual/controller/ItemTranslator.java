package bg.dimitar.individual.controller;

import bg.dimitar.individual.controller.dtos.Item;
import bg.dimitar.individual.persistance.entity.ItemEntity;

public class ItemTranslator {
    public static ItemEntity translateToEntity(Item item) {
        return ItemEntity.builder()
                .name(item.getName())
                .category(item.getCategory())
                .build();

    }

    public static ItemEntity translateToEntity(Item item, long id) {
        return ItemEntity.builder()
                .id(id)
                .name(item.getName())
                .category(item.getCategory())
                .build();
    }

    public static Item translateToDTO(ItemEntity item) {
        return Item.builder()
                .name(item.getName())
                .category(item.getCategory())
                .build();
    }
}
