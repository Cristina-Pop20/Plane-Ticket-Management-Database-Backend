package com.example.springbootbackendappDB.service;

import com.example.springbootbackendappDB.domain.Role;
import com.example.springbootbackendappDB.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepo roleRepo;

    @Autowired
    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Optional<Role> findByName(String roleName) {
        return roleRepo.findByName(roleName);
    }
}
