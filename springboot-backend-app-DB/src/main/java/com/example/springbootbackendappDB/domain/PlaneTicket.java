package com.example.springbootbackendappDB.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "planeTickets")
public class PlaneTicket {
    @Id
    @SequenceGenerator(
            name = "planeTickets_sequence",
            sequenceName = "planeTickets_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "planeTickets_sequence"
    )
    @Column(
            name = "planeTicketId",
            updatable = false
    )
    private Integer planeId;
    @Column(
            name = "departure",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String departure;
    @Column(
            name = "destination",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String destination;
    @Column(
            name = "date",
            nullable = false
    )
    private String date;
    @Column(
            name = "hour",
            nullable = false
    )
    private String hour;
    @Column(
            name = "price",
            nullable = false
    )
    private Float price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public PlaneTicket(Integer planeId, String departure, String destination, String date, String hour, Float price, User user) {
        this.planeId = planeId;
        this.departure = departure;
        this.destination = destination;
        this.date = date;
        this.hour = hour;
        this.price = price;
        this.user = user;
    }

    public PlaneTicket() {

    }

    public Integer getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Integer planeId) {
        this.planeId = planeId;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PlaneTicket{" +
                "planeId=" + planeId +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", price=" + price +
                ", user=" + user +
                '}';
    }
}
