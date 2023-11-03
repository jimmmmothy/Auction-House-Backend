package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.controller.dtos.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5000/", allowedHeaders = "*")
public class RegisterController {
    private final UserManager userManager;

    @PostMapping
    public ResponseEntity<Boolean> registerUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(userManager.addUser(UserTranslator.translateToEntity(user)));
    }
}
