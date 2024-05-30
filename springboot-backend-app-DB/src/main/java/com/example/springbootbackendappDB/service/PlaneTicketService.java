package com.example.springbootbackendappDB.service;

import com.example.springbootbackendappDB.domain.PlaneTicket;

import java.util.List;
import java.util.Optional;

public interface PlaneTicketService {
    public List<PlaneTicket> getAllTickets();

    public List<PlaneTicket> getPaginatedAndSortedPlaneTickets(String sortDirection, Integer pageNumber);

    public void addTicket(PlaneTicket planeTicket);

    public void deleteTicket(Integer id);

    public Optional<PlaneTicket> getTicketById(Integer id);

    public void updateTicket(Integer id, PlaneTicket updatedPlaneTicket);

    public List<PlaneTicket> filterTickets(String departure, String destination, String date, String range);
}
