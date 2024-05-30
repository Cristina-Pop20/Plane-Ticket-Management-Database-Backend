package com.example.springbootbackendappDB.service;

import com.example.springbootbackendappDB.domain.PlaneTicket;
import com.example.springbootbackendappDB.domain.Role;
import com.example.springbootbackendappDB.domain.User;
import com.example.springbootbackendappDB.repository.PlaneTicketRepo;
import com.example.springbootbackendappDB.repository.RoleRepo;
import com.example.springbootbackendappDB.repository.UserRepo;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Component
public class DataInitService implements CommandLineRunner {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PlaneTicketRepo planeTicketRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    int numUsers = 1100;
    int numTicketsPerUser = 100;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        List<User> users = new ArrayList<>();
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN");
        for (int i = 0; i < numUsers; i++) {
            Integer userId = i + 1;
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String rawPassword = faker.internet().password();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            List<PlaneTicket> planeTickets = new ArrayList<>();
            String roleName = roles.get(faker.random().nextInt(roles.size()));
            Optional<Role> role = roleRepository.findByName(roleName);
            if (role.isEmpty()) {
                Role newRole = new Role();
                newRole.setName(roleName);
                role = Optional.of(roleRepository.save(newRole));
            }

            User user = new User(userId, firstName, lastName, email, encodedPassword, planeTickets, Collections.singletonList(role.get()));
            users.add(user);
        }
        if (!users.isEmpty()) {
            userRepository.saveAll(users);
        }

        List<PlaneTicket> tickets = new ArrayList<>();
        users = userRepository.findAll();
        for (User user : users) {
            for (int i = 0; i < numTicketsPerUser; i++) {
                Integer planeTicketId = (user.getUserId() - 1) * numTicketsPerUser + i + 1;
                String departure = faker.address().city();
                String destination = faker.address().city();
                String date = faker.date().future(365, TimeUnit.DAYS).toString();
                String hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                float price = (float) faker.number().randomDouble(2, 10, 2000);

                PlaneTicket ticket = new PlaneTicket(planeTicketId, departure, destination, date, hour, price, user);
                tickets.add(ticket);
            }
        }
        if (!tickets.isEmpty()) {
            planeTicketRepository.saveAll(tickets);
        }
    }
}
