package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.ItemManager;
import bg.dimitar.individual.business.impl.ItemManagerImpl;
import bg.dimitar.individual.controller.dtos.Item;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5000/", allowedHeaders = "*")
public class ItemController {
    private final ItemManager itemManager;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(
                itemManager.getAllItems()
                        .stream()
                        .map(ItemTranslator::translateToDTO).collect(Collectors.toList())
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Item> getItemByID(@PathVariable final long id) {
        if (itemManager.getItemByID(id) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ItemTranslator.translateToDTO(itemManager.getItemByID(id)));
    }

    @PostMapping
    public ResponseEntity<Void> addItem(@RequestBody @Valid Item item) {
        itemManager.addItem(ItemTranslator.translateToEntity(item));

        return ResponseEntity.ok().build();
    }

//    @PutMapping("{id}")
//    public ResponseEntity<Void> updateItem(@RequestBody @Valid Item item, @PathVariable final long id) {
//        if (itemManager.update(ItemTranslator.translateToEntity(item, id)))
//            return ResponseEntity.ok().build();
//
//        return ResponseEntity.notFound().build();
//    }

//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> deleteItem(@PathVariable final long id) {
//        if (itemManager.deleteItem(id))
//            return ResponseEntity.ok().build();
//
//        return ResponseEntity.notFound().build();
//    }
}
