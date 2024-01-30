package com.hostfully.bookingservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"guest\"")
public class Guest extends Person{

    public Guest(){

    }

    public Guest(UUID id, String name, String document, String telephone, String address, String mail, Boolean deleted) {
        super(id, name, document, telephone, address, mail, deleted);
    }
}