package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.persistance.entity.UserEntity;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5000/", allowedHeaders = "*")
public class AdminController {
    private final UserManager userManager;

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userManager.getAllUsers());
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final long userId) {
        try {
            userManager.deleteUser(userId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<Void> makeAdmin(@PathVariable("id") final long userId) {
        try {
            userManager.makeAdmin(userId);
            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
