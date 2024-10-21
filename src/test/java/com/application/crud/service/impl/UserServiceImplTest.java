package com.application.crud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.application.crud.entity.Role;
import com.application.crud.entity.User;
import com.application.crud.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setRoleName("ADMIN");

        user = new User();
        user.setName("Danyal Afzal");
        user.setEmail("danyal.afzal@example.com");
        user.setRole(role);

    }

    @Test
    void testAddUser_Success() {
        when(userRepository.save(any())).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testAddUser_EmailAlreadyExists() {
        when(userRepository.save(any(User.class))).thenThrow(new DataIntegrityViolationException(""));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });

        assertEquals("Email already exists: " + user.getEmail(), exception.getMessage());
    }

    @Test
    void testEditUser_Success() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setName("Danyal Cheema");
        updatedUser.setEmail("danyal.cheema@example.com");
        updatedUser.setRole(role);

        User result = userService.editUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals("Danyal Cheema", result.getName());
        assertEquals("danyal.cheema@example.com", result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testEditUser_UserNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.editUser(1L, user);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindUsersByRoleName() {
        when(userRepository.findUsersByRoleName(any(String.class))).thenReturn(List.of(user));

        List<User> users = userService.findUsersByRoleName("ADMIN");

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(user.getName(), users.get(0).getName());
    }

    @Test
    void testSearchUsers_WithName() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(List.of(user));
        when(userRepository.findByNameContainingIgnoreCase(any(String.class), any(Pageable.class))).thenReturn(page);

        Page<User> result = userService.searchUsers("John", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(user.getName(), result.getContent().get(0).getName());
    }

    @Test
    void testSearchUsers_WithoutName() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> page = new PageImpl<>(List.of(user));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<User> result = userService.searchUsers("", 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testValidateUser_NoName() {
        user.setName("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });

        assertEquals("User name is required", exception.getMessage());
    }

    @Test
    void testValidateUser_NoEmail() {
        user.setEmail("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });

        assertEquals("User email is required", exception.getMessage());
    }

    @Test
    void testValidateUser_NoRole() {
        user.setRole(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });

        assertEquals("Role is required", exception.getMessage());
    }
}
