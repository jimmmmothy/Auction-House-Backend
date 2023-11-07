import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.business.impl.UserManagerImpl;
import bg.dimitar.individual.business.custom_exception.InvalidRegistrationException;
import bg.dimitar.individual.persistance.UserRepository;
import bg.dimitar.individual.persistance.entity.UserEntity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

class UserManagerTest {
    private UserManager userManager;
    private UserRepository repository;

    @BeforeEach
    public void setUp() {
        repository = mock(UserRepository.class);
        userManager = new UserManagerImpl(repository);
    }

    @Test
    void getUserById_Success() {
        Long userId = 1L;
        UserEntity expected = new UserEntity();
        expected.setId(userId);

        when(repository.findById(userId)).thenReturn(Optional.of(expected));

        UserEntity actual = userManager.getUserById(userId);

        assertEquals(expected, actual);
    }

    @Test
    void getUserById_ReturnsNull_WhenUserDoesNotExist() {
        Long userId = 1L;

        when(repository.findById(userId)).thenReturn(Optional.empty());

        UserEntity actual = userManager.getUserById(userId);

        assertNull(actual);
    }

    @Test
    void getUserByEmail_Success() {
        String email = "user@user.com";
        UserEntity expected = new UserEntity();
        expected.setEmail(email);

        when(repository.findByEmail(email)).thenReturn(Optional.of(expected));

        UserEntity actual = userManager.getUserByEmail(email);

        assertEquals(expected, actual);
    }

    @Test
    void getUserByEmail_ReturnsNull_WhenUserDoesNotExist() {
        String email = "user@user.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        UserEntity actual = userManager.getUserByEmail(email);

        assertNull(actual);
    }

    @Test
    void getUserByEmailAndPassword_Success() {
        String email = "user@example.com";
        String password = "password";
        UserEntity expected = new UserEntity();
        expected.setEmail(email);
        expected.setPassword(password);

        when(repository.findByEmailAndPassword(email, password)).thenReturn(Optional.of(expected));

        UserEntity actual = userManager.getUserByEmailAndPassword(email, password);

        assertEquals(expected, actual);
    }

    @Test
    void getUserByEmailAndPassword_ReturnsNull_MatchingEmailAndPasswordNotFound() {
        String email = "user@example.com";
        String password = "password";

        when(repository.findByEmailAndPassword(email, password)).thenReturn(Optional.empty());

        UserEntity result = userManager.getUserByEmailAndPassword(email, password);

        assertNull(result);
    }

    @Test
    void addUser_Success() throws InvalidRegistrationException {
        UserEntity user = new UserEntity();

        when(repository.save(any(UserEntity.class))).thenReturn(user);

        boolean result = userManager.addUser(user);

        verify(repository, times(1)).save(any(UserEntity.class));

        assertTrue(result);
    }

    @Test
    void addUser_ThrowsException_WhenConstraintViolation() throws ConstraintViolationException {
        UserEntity user = new UserEntity();

        Set<ConstraintViolation<UserEntity>> violations = new HashSet<>(1);

        when(repository.save(any(UserEntity.class))).thenThrow(new ConstraintViolationException(violations));

        assertThrows(InvalidRegistrationException.class,() -> userManager.addUser(user));

        verify(repository, times(1)).save(any(UserEntity.class));
    }
}
