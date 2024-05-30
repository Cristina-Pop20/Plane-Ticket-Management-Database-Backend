package com.example.springbootbackendappDB.service;

import com.example.springbootbackendappDB.domain.Role;
import com.example.springbootbackendappDB.domain.User;
import com.example.springbootbackendappDB.repository.RoleRepo;
import com.example.springbootbackendappDB.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;

    @Autowired
    public UserRoleServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        Optional<Role> roleOptional = roleRepo.findByName(roleName);

        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();
            user.getRoles().add(role);
            userRepo.save(user);
        }
    }
}
