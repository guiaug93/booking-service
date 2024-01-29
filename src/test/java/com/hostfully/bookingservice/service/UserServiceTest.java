package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.User;
import com.hostfully.bookingservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() {
        User user = new User(UUID.randomUUID(), "John Doe", "john@example.com", "password", false);
        when(userRepository.findByMail("john@example.com")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.create(user);

        assertNotNull(savedUser);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getMail(), savedUser.getMail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertFalse(savedUser.isDeleted());
        verify(userRepository, times(1)).findByMail("john@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_DuplicateMail() {
        User existingUser = new User(UUID.randomUUID(), "John Doe", "john@example.com", "password", false);
        when(userRepository.findByMail("john@example.com")).thenReturn(existingUser);

        assertThrows(ServiceException.class, () -> userService.create(existingUser));

        verify(userRepository, times(1)).findByMail("john@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUser_Success() {
        UUID userId = UUID.randomUUID();
        User existingUser = new User(userId, "John Doe", "john@example.com", "password", false);
        User updatedUser = new User(userId, "Jane Doe", "jane@example.com", "newpassword", false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(userId, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getMail(), result.getMail());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertFalse(result.isDeleted());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        UUID userId = UUID.randomUUID();
        User updatedUser = new User(userId, "Jane Doe", "jane@example.com", "newpassword", false);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> userService.update(userId, updatedUser));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

}