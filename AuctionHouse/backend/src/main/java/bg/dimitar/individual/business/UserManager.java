package bg.dimitar.individual.business;


import bg.dimitar.individual.business.custom_exception.EmailInUseException;
import bg.dimitar.individual.business.custom_exception.InvalidLoginException;
import bg.dimitar.individual.business.custom_exception.InvalidRegistrationException;
import bg.dimitar.individual.persistance.entity.UserEntity;

import java.util.List;

public interface UserManager {
    UserEntity getUserById(Long id);
    UserEntity getUserByEmail(String email);
    UserEntity getUserByEmailAndPassword(String email, String password);
    List<UserEntity> getAllUsers();
    boolean addUser(UserEntity user) throws InvalidRegistrationException, EmailInUseException;
    UserEntity authenticateUser(UserEntity user) throws InvalidLoginException;
    boolean updateUser(UserEntity user);
    void deleteUser(Long id);
    void makeAdmin(Long id);
}
