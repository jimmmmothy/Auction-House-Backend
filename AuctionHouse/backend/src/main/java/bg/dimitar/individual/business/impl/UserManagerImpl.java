package bg.dimitar.individual.business.impl;


import bg.dimitar.individual.business.UserManager;

import bg.dimitar.individual.business.custom_exception.InvalidRegistrationException;
import bg.dimitar.individual.persistance.UserRepository;
import bg.dimitar.individual.persistance.entity.UserEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserManagerImpl implements UserManager {
    private UserRepository repository;

    @Override
    public UserEntity getUserById(Long id) {
        Optional<UserEntity> userOptional = repository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        Optional<UserEntity> userOptional = repository.findByEmail(email);
        return userOptional.orElse(null);
    }

    @Override
    public UserEntity getUserByEmailAndPassword(String email, String password) {
        Optional<UserEntity> userOptional = repository.findByEmailAndPassword(email, password);
        return userOptional.orElse(null);
    }

    @Override
    public boolean addUser(UserEntity user) throws InvalidRegistrationException {
        try {
            repository.save(user);
            return true;
        }
        catch (ConstraintViolationException e) {
            throw new InvalidRegistrationException(
                    e.getConstraintViolations()
                            .stream()
                            .findFirst()
                            .map(ConstraintViolation::getMessage)
                            .orElse("Unknown constraint violation")
            );
        }
    }
}
