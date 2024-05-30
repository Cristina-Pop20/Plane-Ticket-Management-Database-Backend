package com.example.springbootbackendappDB.repository;

import com.example.springbootbackendappDB.domain.PlaneTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneTicketRepo extends JpaRepository<PlaneTicket, Integer> {
    @Query("SELECT p FROM PlaneTicket p ORDER BY "
            + "CASE WHEN :sortDirection = 'ASC' THEN p.price END ASC, "
            + "CASE WHEN :sortDirection = 'DESC' THEN p.price END DESC, "
            + "CASE WHEN :sortDirection = 'A-Z' THEN p.departure END ASC, "
            + "CASE WHEN :sortDirection = 'Most Recent Added' THEN p.planeId END DESC")
    List<PlaneTicket> getPaginatedAndSortedPlaneTickets(
            @Param("sortDirection") String sortDirection
    );

    @Query("SELECT p FROM PlaneTicket p " +
            "WHERE (:departure IS NULL OR p.departure = :departure) " +
            "AND (:destination = 'Anywhere' OR p.destination LIKE %:destination%) " +
            "AND (:date IS NULL OR p.date = :date) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) ")
    List<PlaneTicket> filerTickets(
            @Param("departure") String departure,
            @Param("destination") String destination,
            @Param("date") String date,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice
    );
}
