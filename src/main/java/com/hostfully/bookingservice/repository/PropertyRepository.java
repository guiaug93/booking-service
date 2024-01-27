package com.hostfully.bookingservice.repository;

import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {
    Property findByName(String name);
    List<Property> findByDeletedFalse();
}