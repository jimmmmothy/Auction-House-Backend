package bg.dimitar.individual.business.impl;


import bg.dimitar.individual.business.UserManager;

import bg.dimitar.individual.business.custom_exception.EmailInUseException;
import bg.dimitar.individual.business.custom_exception.InvalidLoginException;
import bg.dimitar.individual.business.custom_exception.InvalidRegistrationException;
import bg.dimitar.individual.persistance.UserRepository;
import bg.dimitar.individual.persistance.entity.UserEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserManagerImpl implements UserManager {
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
    public List<UserEntity> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public boolean addUser(UserEntity user) throws InvalidRegistrationException, EmailInUseException {
        try {
            String hashedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPass);
            if (repository.findByEmail(user.getEmail()).isPresent()) {
                throw new EmailInUseException();
            }
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

    @Override
    public UserEntity authenticateUser(UserEntity user) throws InvalidLoginException {
        Optional<UserEntity> returned = repository.findByEmail(user.getEmail());

        if (returned.isEmpty())
            return null;

        if (passwordEncoder.matches(user.getPassword(), returned.get().getPassword())) {
            return returned.get();
        }

        throw new InvalidLoginException("Invalid login credentials");
    }

    @Override
    public boolean updateUser(UserEntity user) {
        Optional<UserEntity> userFromDb = repository.findById(user.getId());

        if (userFromDb.isEmpty()) {
            return false;
        }

        UserEntity existingUser = userFromDb.get();

        if (!user.getPassword().startsWith("$2a$")) {
            String hashedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPass);
        } else {
            user.setPassword(existingUser.getPassword());
        }

        repository.save(user);
        return true;
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void makeAdmin(Long id) {
        Optional<UserEntity> userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            user.setRole("admin");
            repository.save(user);
        }
    }
}
