package com.hostfully.bookingservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "\"guest\"")
public class Guest extends Person{

    //@OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    //private List<Booking> bookings;

}