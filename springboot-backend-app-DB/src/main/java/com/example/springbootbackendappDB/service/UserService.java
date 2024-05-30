package com.example.springbootbackendappDB.service;

import com.example.springbootbackendappDB.domain.PlaneTicket;
import com.example.springbootbackendappDB.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> getAllUsers();

    public void addUser(User user);

    public void deleteUser(Integer id);

    public Optional<User> getUserById(Integer id);

    public void updateUser(Integer id, User updatedUser);

    public Optional<User> findByEmailAndPassword(String email, String rawPassword);

    List<User> getUsersInRange(int start, int size);
}
