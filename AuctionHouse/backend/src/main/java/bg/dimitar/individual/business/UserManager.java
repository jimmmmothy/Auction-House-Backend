package bg.dimitar.individual.business;


import bg.dimitar.individual.business.custom_exception.InvalidLoginException;
import bg.dimitar.individual.business.custom_exception.InvalidRegistrationException;
import bg.dimitar.individual.persistance.entity.UserEntity;

public interface UserManager {
    UserEntity getUserById(Long id);
    UserEntity getUserByEmail(String email);
    UserEntity getUserByEmailAndPassword(String email, String password);
    boolean addUser(UserEntity user) throws InvalidRegistrationException;
    UserEntity authenticateUser(UserEntity user) throws InvalidLoginException;
}
