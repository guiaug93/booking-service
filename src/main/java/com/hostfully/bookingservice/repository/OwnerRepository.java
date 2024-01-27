package com.hostfully.bookingservice.repository;

import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID> {

    Owner findByDocument(String document);
    List<Owner> findByDeletedFalse();
}