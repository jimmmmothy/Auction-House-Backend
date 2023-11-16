package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.ItemManager;
import bg.dimitar.individual.business.custom_exception.NotFoundException;
import bg.dimitar.individual.business.custom_exception.UnauthorizedChangeException;
import bg.dimitar.individual.controller.dtos.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                        .map(ItemTranslator::translateToDTO).toList()
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
        try {
            itemManager.addItem(ItemTranslator.translateToEntity(item));
            return ResponseEntity.ok().build();
        }
        catch (JsonProcessingException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateItem(@RequestBody @Valid Item item, @PathVariable("id") final Long itemId, Long userId) {
        try {
            if (itemManager.updateItem(ItemTranslator.translateToEntity(item, itemId), userId))
                return ResponseEntity.ok().build();

            return ResponseEntity.notFound().build();
        }
        catch (UnauthorizedChangeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (JsonProcessingException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteItem(@PathVariable("id") final Long itemId, Long userId) {
        try {
            itemManager.deleteItem(itemId, userId);

            return ResponseEntity.ok().build();
        }
        catch (UnauthorizedChangeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
