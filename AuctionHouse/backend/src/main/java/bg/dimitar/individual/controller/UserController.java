package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.controller.dtos.FullUser;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5000/", allowedHeaders = "*")
public class UserController {
    private final UserManager userManager;

    @GetMapping("/{id}")
    public ResponseEntity<FullUser> getUserById(@PathVariable("id") final long userId, Principal principal) {
        if (Integer.parseInt(principal.getName()) == userId) {
            return ResponseEntity.ok(UserTranslator.translateToFullDTO(userManager.getUserById(userId)));
        }

        return ResponseEntity.status(403).build();
    }

    @PutMapping("/self/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody final FullUser user, Principal principal) {
        try {
            if (Integer.parseInt(principal.getName()) != user.getId()) {
                return ResponseEntity.status(403).build();
            }

            userManager.updateUser(UserTranslator.translate(user));
            return ResponseEntity.ok().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @RolesAllowed("admin")
    public ResponseEntity<List<FullUser>> getAllUsers() {
        return ResponseEntity.ok(userManager.getAllUsers().stream().map(UserTranslator::translateToFullDTO).toList());
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
