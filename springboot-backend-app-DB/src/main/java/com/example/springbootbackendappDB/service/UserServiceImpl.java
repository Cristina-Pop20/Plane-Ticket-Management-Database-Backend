package com.example.springbootbackendappDB.service;

import com.example.springbootbackendappDB.domain.PlaneTicket;
import com.example.springbootbackendappDB.domain.Role;
import com.example.springbootbackendappDB.domain.User;
import com.example.springbootbackendappDB.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

    }

    @Override
    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepo.findById(id);
    }

    @Override
    public void updateUser(Integer id, User updatedUser) {
        userRepo.findById(id).ifPresent(existingUser -> {
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            if (!existingUser.getPassword().equals(updatedUser.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Encode the new password
            }
            userRepo.save(existingUser);
        });
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String rawPassword) {
        Optional<User> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

    @Override
    public List<User> getUsersInRange(int start, int size) {
        return userRepo.getUsersInRange(start, size);
    }


}
