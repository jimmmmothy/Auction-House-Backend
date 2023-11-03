package bg.dimitar.individual.controller;

import bg.dimitar.individual.controller.dtos.Item;
import bg.dimitar.individual.persistance.entity.ItemEntity;

public class ItemTranslator {
    private ItemTranslator(){}
    public static ItemEntity translateToEntity(Item item) {
        return ItemEntity.builder()
                .title(item.getTitle())
                .category(item.getCategory())
                .startingPrice(item.getStartingPrice())
                .currentBid(item.getCurrentBid())
                .build();
    }

    public static ItemEntity translateToEntity(Item item, long id) {
        return ItemEntity.builder()
                .id(id)
                .title(item.getTitle())
                .category(item.getCategory())
                .startingPrice(item.getStartingPrice())
                .currentBid(item.getCurrentBid())
                .build();
    }

    public static Item translateToDTO(ItemEntity item) {
        return Item.builder()
                .title(item.getTitle())
                .category(item.getCategory())
                .startingPrice(item.getStartingPrice())
                .currentBid(item.getCurrentBid())
                .build();
    }
}
