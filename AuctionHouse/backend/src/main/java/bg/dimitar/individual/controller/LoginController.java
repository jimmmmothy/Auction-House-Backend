package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.business.custom_exception.InvalidLoginException;
import bg.dimitar.individual.configuration.security.token.AccessTokenEncoder;
import bg.dimitar.individual.configuration.security.token.impl.AccessTokenImpl;
import bg.dimitar.individual.controller.dtos.Login;
import bg.dimitar.individual.persistance.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5000/", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private final AccessTokenEncoder accessTokenEncoder;
    private final UserManager userManager;

    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody @Valid Login login) {
        try {
            UserEntity entity = UserTranslator.translate(login);
            entity = userManager.authenticateUser(entity);

            if (entity == null) {
                throw new InvalidLoginException("Invalid email");
            }

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(accessTokenEncoder.encode(new AccessTokenImpl(entity)));
        }
        catch (InvalidLoginException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
