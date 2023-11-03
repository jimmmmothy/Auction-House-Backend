package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.persistance.UserRepository;
import bg.dimitar.individual.persistance.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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

//    @Override
//    public UserEntity getUserByEmailAndPassword(String email, String password) {
//        Optional<UserEntity> userOptional = repository.findbyEmailAndPassword(email, password);
//        return userOptional.orElse(null);
//    }

    @Override
    public boolean addUser(UserEntity user) {
        repository.save(user);
        return true;
    }
}
