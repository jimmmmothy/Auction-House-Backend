package business;

import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.business.impl.UserManagerImpl;
import bg.dimitar.individual.business.customException.InvalidRegistrationException;
import bg.dimitar.individual.persistance.UserRepository;
import bg.dimitar.individual.persistance.entity.UserEntity;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

public class UserManagerTest {
    private UserManager userManager;
    private UserRepository repository;

    @BeforeEach
    public void setUp() {
        repository = mock(UserRepository.class);
        userManager = new UserManagerImpl(repository);
    }

    @Test
    public void addUser_Success() throws InvalidRegistrationException {
        UserEntity user = new UserEntity();
        user.setPassword("12345678");

        when(repository.save(any(UserEntity.class))).thenReturn(user);

        boolean result = userManager.addUser(user);

        verify(repository, times(1)).save(any(UserEntity.class));

        assertTrue(result);
    }

//    @Test
//    public void addUser_ThrowsException_WhenConstraintViolation() {
//        UserEntity user = new UserEntity();
//        user.setPassword("12345678");
//
//        when(repository.save(any(UserEntity.class))).thenThrow(ConstraintViolationException.class);
//
//        assertThrows(InvalidRegistrationException.class, () -> userManager.addUser(user));
//
//        verify(repository, times(1)).save(any(UserEntity.class));
//    }
}
