package com.example.springbootbackendappDB.service;

import com.example.springbootbackendappDB.domain.PlaneTicket;
import com.example.springbootbackendappDB.domain.User;
import com.example.springbootbackendappDB.repository.PlaneTicketRepo;
import com.example.springbootbackendappDB.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@DependsOn("userServiceImpl")
public class PlaneTicketServiceImpl implements PlaneTicketService {
    private PlaneTicketRepo planeTicketRepo;

    @Autowired
    public PlaneTicketServiceImpl(PlaneTicketRepo planeTicketRepo) {
        this.planeTicketRepo = planeTicketRepo;
    }


    @Override
    public List<PlaneTicket> getAllTickets() {
        return planeTicketRepo.findAll();
    }

    @Override
    public List<PlaneTicket> getPaginatedAndSortedPlaneTickets(String sortDirection, Integer pageNumber) {
        List<PlaneTicket> sortedList = planeTicketRepo.getPaginatedAndSortedPlaneTickets(sortDirection);
        int pageSize = pageNumber;
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, sortedList.size());
        System.out.println("startIndex" + startIndex + "endIndex" + endIndex);
        return sortedList;
    }

    @Override
    public void addTicket(PlaneTicket planeTicket) {
        planeTicketRepo.save(planeTicket);
    }

    @Override
    public void deleteTicket(Integer id) {
        Optional<PlaneTicket> ticketToDelete = planeTicketRepo.findById(id);
        if (ticketToDelete.isPresent()) {
            planeTicketRepo.deleteById(id);
            System.out.println("Ticket with ID " + id + " deleted successfully.");
        } else {
            System.out.println("Ticket with ID " + id + " not found. Deletion failed.");
        }
    }

    @Override
    public Optional<PlaneTicket> getTicketById(Integer id) {
        return planeTicketRepo.findById(id);
    }

    @Override
    public void updateTicket(Integer id, PlaneTicket updatedPlaneTicket) {
        planeTicketRepo.findById(id).ifPresent(existingPlaneTicket -> {
            existingPlaneTicket.setDeparture(updatedPlaneTicket.getDeparture());
            existingPlaneTicket.setDestination(updatedPlaneTicket.getDestination());
            existingPlaneTicket.setDate(updatedPlaneTicket.getDate());
            existingPlaneTicket.setHour(updatedPlaneTicket.getHour());
            existingPlaneTicket.setPrice(updatedPlaneTicket.getPrice());
            planeTicketRepo.save(existingPlaneTicket);
        });
    }

    @Override
    public List<PlaneTicket> filterTickets(String departure, String destination, String date, String range) {
        Float minPrice = null;
        Float maxPrice = null;

        if (range != null && !range.isEmpty()) {
            String[] rangeParts = range.split("-");
            if (rangeParts.length == 1) {
                String operator = rangeParts[0].substring(0, 1);
                String priceValue = rangeParts[0].substring(1);
                if (operator.equals("<")) {
                    maxPrice = Float.parseFloat(priceValue);
                } else if (operator.equals(">")) {
                    minPrice = Float.parseFloat(priceValue);
                }
            } else if (rangeParts.length == 2) {
                minPrice = Float.parseFloat(rangeParts[0]);
                maxPrice = Float.parseFloat(rangeParts[1]);
            }
        }

        return planeTicketRepo.filerTickets(departure, destination, date, minPrice, maxPrice);
    }
}
