package com.example.springbootbackendappDB.controller;

import com.example.springbootbackendappDB.domain.PlaneTicket;
import com.example.springbootbackendappDB.service.PlaneTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PlaneTicketController {
    private PlaneTicketService planeTicketService;

    @Autowired
    public PlaneTicketController(PlaneTicketService planeTicketService) {
        this.planeTicketService = planeTicketService;
    }

    @GetMapping("/{id}")
    public Optional<PlaneTicket> getPlaneTicket(@PathVariable("id") Integer id) {
        return planeTicketService.getTicketById(id);
    }

    @GetMapping("/displayAllTickets")
    public List<PlaneTicket> getAllPlaneTickets() {
        return planeTicketService.getAllTickets();
    }

    @GetMapping("/display")
    public List<PlaneTicket> getPaginatedAndSortedPlaneTickets(
            @RequestParam String sortDirection,
            @RequestParam Integer pageNumber) {
        return planeTicketService.getPaginatedAndSortedPlaneTickets(sortDirection, pageNumber);
    }


    @PostMapping("/addTicket")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public void addPlaneTicket(@RequestBody PlaneTicket planeTicket) {
        planeTicketService.addTicket(planeTicket);
    }

    @DeleteMapping("/deleteTicket/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public void deletePlaneTicket(@PathVariable Integer id) {
        planeTicketService.deleteTicket(id);
    }

    @PutMapping("/updateTicket/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public void updatePlaneTicket(@PathVariable Integer id, @RequestBody PlaneTicket planeTicket) {
        planeTicketService.updateTicket(id, planeTicket);
    }

    @GetMapping("/filterTickets")
    public List<PlaneTicket> filterTickets(
            @RequestParam String departure,
            @RequestParam String destination,
            @RequestParam String date,
            @RequestParam String range) {
        return planeTicketService.filterTickets(departure, destination, date, range);
    }
}