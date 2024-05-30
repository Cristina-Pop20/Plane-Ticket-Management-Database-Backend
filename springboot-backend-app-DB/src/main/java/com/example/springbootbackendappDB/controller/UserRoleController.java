package com.example.springbootbackendappDB.controller;

import com.example.springbootbackendappDB.service.UserRoleService;
import com.example.springbootbackendappDB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/role/addToUser")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRoleToUser(@RequestBody String email, @RequestParam String roleName) {
        userRoleService.addRoleToUser(email, roleName);
    }
}
