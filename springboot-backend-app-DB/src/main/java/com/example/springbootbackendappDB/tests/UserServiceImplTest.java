package com.example.springbootbackendappDB.tests;

import com.example.springbootbackendappDB.domain.Role;
import com.example.springbootbackendappDB.domain.User;
import com.example.springbootbackendappDB.repository.UserRepo;
import com.example.springbootbackendappDB.service.UserService;
import com.example.springbootbackendappDB.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    private UserService userService;

    @Before
    public void setUp() {
        UserRepo userRepo = Mockito.mock(UserRepo.class);
        userService = new UserServiceImpl(userRepo);
    }

    @Test
    public void testAddUser() {
        List<Role> roles = Arrays.asList(new Role(1, "ROLE_USER"), new Role(2, "ROLE_MANAGER"), new Role(3, "ROLE_ADMIN"));
        User newUser = new User(4, "New", "User", "new.user@example.com", "password", new ArrayList<>(), Collections.singletonList(roles.get(0)));
        userService.addUser(newUser);
        User retrievedUser = userService.getUserById(4).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(newUser.getUserId(), retrievedUser.getUserId());
        assertEquals(newUser.getFirstName(), retrievedUser.getFirstName());
        assertEquals(newUser.getLastName(), retrievedUser.getLastName());
        assertEquals(newUser.getEmail(), retrievedUser.getEmail());
        assertEquals(newUser.getPassword(), retrievedUser.getPassword());
    }

    @Test
    public void testGetUserById() {
        User user = userService.getUserById(1).orElse(null);
        assertNotNull(user);
        assertEquals(Integer.valueOf(1), user.getUserId());
    }

    @Test
    public void testUpdateUser() {
        Integer idToUpdate = 1;
        List<Role> roles = Arrays.asList(new Role(1, "ROLE_USER"), new Role(2, "ROLE_MANAGER"), new Role(3, "ROLE_ADMIN"));
        User updatedUser = new User(idToUpdate, "Updated", "User", "updated.user@example.com", "updatedPassword", new ArrayList<>(), Collections.singletonList(roles.get(1)));
        userService.updateUser(idToUpdate, updatedUser);

        Optional<User> updated = userService.getUserById(idToUpdate);
        assertTrue(updated.isPresent());
        assertEquals(updatedUser.getFirstName(), updated.get().getFirstName());
        assertEquals(updatedUser.getLastName(), updated.get().getLastName());
        assertEquals(updatedUser.getEmail(), updated.get().getEmail());
        assertEquals(updatedUser.getPassword(), updated.get().getPassword());
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(3);
        User deletedUser = userService.getUserById(3).orElse(null);
        assertNull(deletedUser);
    }
}
