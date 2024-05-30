package com.example.springbootbackendappDB.service;

import com.example.springbootbackendappDB.domain.Role;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface RoleService {
    Role saveRole(Role role);

    Optional<Role> findByName(String roleName);
}
