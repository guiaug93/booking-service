package com.hostfully.bookingservice.model;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"owner\"")
public class Owner extends Person{

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Property> properties;

    public Owner() {

    }

    public Owner(UUID id, String name, String document, String telephone, String address, String mail, Boolean deleted, List<Property> properties) {
        super(id, name, document, telephone, address, mail, deleted);
        this.properties = properties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
}