package com.example.springbootbackendappDB.service;

import org.springframework.stereotype.Service;


public interface UserRoleService {
    public void addRoleToUser(String email, String roleName);
}
