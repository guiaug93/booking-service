package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.model.Property;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InitDatabaseService {


    private final OwnerService ownerService;
    private final GuestService guestService;
    private final PropertyService propertyService;

    public InitDatabaseService(OwnerService ownerService, GuestService guestService, PropertyService propertyService) {
        this.ownerService = ownerService;
        this.guestService = guestService;
        this.propertyService = propertyService;
    }

    @PostConstruct
    public void init() {
        createMockOwner();
        createMockGuest();
        createMockProperty();
    }

    private void createMockOwner() {
        Owner owner = new Owner();
        owner.setDocument("123");
        owner.setMail("test@test.com");
        owner.setDeleted(false);
        owner.setName("Mock Owner");
        owner.setTelephone("12");
        owner.setAddress("Address 123");
        ownerService.create(owner);
    }

    private void createMockGuest() {
        Guest guest = new Guest();
        guest.setDocument("123");
        guest.setMail("test@test.com");
        guest.setDeleted(false);
        guest.setName("Mock Guest");
        guest.setTelephone("12");
        guest.setAddress("Address 123");
        guestService.create(guest);
    }

    private void createMockProperty(){
        List<Owner> owners = ownerService.fetchAll();
        Owner owner = owners.stream()
                .findFirst()
                .orElse(null);
        Property property = new Property();
        property.setOwner(owner);
        property.setName("Beach House");
        property.setDescription("A beautiful beach house");
        property.setDailyValue(new BigDecimal("100"));
        property.setCleaningValue(new BigDecimal("20"));
        propertyService.create(property);
    }

}