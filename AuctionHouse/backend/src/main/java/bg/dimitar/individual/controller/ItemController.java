package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.impl.ItemManagerImpl;
import bg.dimitar.individual.controller.dtos.Item;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemManagerImpl itemManager;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemManager.getAllItems());
    }

    @GetMapping("{id}")
    public ResponseEntity<Item> getItemByID(@PathVariable final long id) {
        if (itemManager.getItemByID(id) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(itemManager.getItemByID(id));
    }

    @PostMapping
    public ResponseEntity<Void> addItem(@RequestBody Item item) {
        itemManager.addItem(item);

        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateItem(@RequestBody Item item, @PathVariable final long id) {
        if (itemManager.updateItem(id, item))
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable final long id) {
        if (itemManager.deleteItem(id))
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }
}
