package bg.dimitar.individual.controller;

import bg.dimitar.individual.business.custom_exception.EmailInUseException;
import bg.dimitar.individual.business.custom_exception.InvalidRegistrationException;
import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.configuration.security.token.AccessTokenEncoder;
import bg.dimitar.individual.configuration.security.token.impl.AccessTokenImpl;
import bg.dimitar.individual.controller.dtos.Register;
import bg.dimitar.individual.persistance.entity.UserEntity;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5000/", allowedHeaders = "*")
public class RegisterController {
    @Autowired
    private final AccessTokenEncoder accessTokenEncoder;
    private final UserManager userManager;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody @Valid Register register) {
        try {
            UserEntity entity = UserTranslator.translate(register);
            userManager.addUser(entity);

            return ResponseEntity.status(HttpStatus.CREATED).body(accessTokenEncoder.encode(new AccessTokenImpl(entity)));
        }
        catch (InvalidRegistrationException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        catch (EmailInUseException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }
}
