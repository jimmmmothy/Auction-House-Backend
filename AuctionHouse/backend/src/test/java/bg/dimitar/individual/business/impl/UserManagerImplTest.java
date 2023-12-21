package bg.dimitar.individual.business.impl;

import bg.dimitar.individual.business.UserManager;
import bg.dimitar.individual.business.custom_exception.EmailInUseException;
import bg.dimitar.individual.business.custom_exception.InvalidLoginException;
import bg.dimitar.individual.business.custom_exception.InvalidRegistrationException;
import bg.dimitar.individual.persistance.UserRepository;
import bg.dimitar.individual.persistance.entity.UserEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserManagerImplTest {
    private UserManager userManager;
    private UserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        repository = mock(UserRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        userManager = new UserManagerImpl(repository, passwordEncoder);
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
    void getAllUsers_Success() {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();

        when(repository.findAll()).thenReturn(List.of(user1, user2));

        List<UserEntity> result = userManager.getAllUsers();

        assertEquals(2, result.size());
    }

    @Test
    void addUser_Success() throws InvalidRegistrationException, EmailInUseException {
        UserEntity user = new UserEntity();

        when(repository.save(any(UserEntity.class))).thenReturn(user);

        boolean result = userManager.addUser(user);

        verify(repository, times(1)).save(any(UserEntity.class));

        assertTrue(result);
    }

    @Test
    void addUser_ThrowsException_WhenEmailInUse() {
        UserEntity user = new UserEntity();
        user.setEmail("email@email.com");

        when(repository.findByEmail("email@email.com")).thenReturn(Optional.of(user));

        assertThrows(EmailInUseException.class,() -> userManager.addUser(user));

        verify(repository, times(0)).save(any(UserEntity.class));
    }

    @Test
    void addUser_ThrowsException_WhenConstraintViolation() throws ConstraintViolationException {
        UserEntity user = new UserEntity();

        Set<ConstraintViolation<UserEntity>> violations = new HashSet<>(1);

        when(repository.save(any(UserEntity.class))).thenThrow(new ConstraintViolationException(violations));

        assertThrows(InvalidRegistrationException.class,() -> userManager.addUser(user));

        verify(repository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void authenticateUser_Success() throws InvalidLoginException {
        UserEntity user = new UserEntity();
        user.setEmail("email@email.com");
        user.setPassword("password");

        when(repository.findByEmail("email@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("password"), anyString())).thenReturn(true);

        UserEntity result = userManager.authenticateUser(user);

        assertEquals(user, result);
    }

    @Test
    void authenticateUser_ReturnsNull_WhenNotFound() throws InvalidLoginException {
        UserEntity user = new UserEntity();
        user.setEmail("email@email.com");
        user.setPassword("password");

        when(repository.findByEmail("email@email.com")).thenReturn(Optional.empty());

        UserEntity result = userManager.authenticateUser(user);

        assertNull(result);
    }

    @Test
    void authenticateUser_ThrowsException_WhenUnauthorized() throws InvalidLoginException{
        UserEntity user = new UserEntity();
        user.setEmail("email@email.com");
        user.setPassword("password");

        when(repository.findByEmail("email@email.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("password"), anyString())).thenReturn(false);

        assertThrows(InvalidLoginException.class, () -> userManager.authenticateUser(user));
    }

    @Test
    void deleteUser_Success() {
        Long userId = 1L;

        userManager.deleteUser(userId);

        verify(repository, times(1)).deleteById(userId);
    }

    @Test
    void makeAdmin_Success() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        userManager.makeAdmin(userId);

        verify(repository, times(1)).save(user);
    }
}