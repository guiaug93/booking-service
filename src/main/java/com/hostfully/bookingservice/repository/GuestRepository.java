package com.hostfully.bookingservice.repository;

import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    Guest findByDocument(String document);
    List<Guest> findByDeletedFalse();

}