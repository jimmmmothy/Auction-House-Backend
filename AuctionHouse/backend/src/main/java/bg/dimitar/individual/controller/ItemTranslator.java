package bg.dimitar.individual.controller;

import bg.dimitar.individual.controller.dtos.Item;
import bg.dimitar.individual.persistance.entity.ItemEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemTranslator {
    private ItemTranslator(){}
    public static ItemEntity translate(Item item) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDescription = objectMapper.writeValueAsString(item.getDescription());

        return ItemEntity.builder()
                .title(item.getTitle())
                .category(item.getCategory())
                .startingPrice(item.getStartingPrice())
                .currentBid(item.getCurrentBid())
                .description(jsonDescription)
                .postedByUserId(item.getPostedByUserId())
                .imageURLs(item.getImageURLs())
                .build();
    }

    public static ItemEntity translate(Item item, long id) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDescription = objectMapper.writeValueAsString(item.getDescription());

        return ItemEntity.builder()
                .id(id)
                .title(item.getTitle())
                .category(item.getCategory())
                .startingPrice(item.getStartingPrice())
                .currentBid(item.getCurrentBid())
                .description(jsonDescription)
                .postedByUserId(item.getPostedByUserId())
                .imageURLs(item.getImageURLs())
                .build();
    }

    public static Item translate(ItemEntity item) {
        return Item.builder()
                .id(item.getId())
                .title(item.getTitle())
                .category(item.getCategory())
                .startingPrice(item.getStartingPrice())
                .currentBid(item.getCurrentBid())
                .description(item.getDescription())
                .postedByUserId(item.getPostedByUserId())
                .imageURLs(item.getImageURLs())
                .build();
    }
}
