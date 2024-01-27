package com.hostfully.bookingservice.repository;

import com.hostfully.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
   /* @Query("SELECT b FROM Booking b " +
            "WHERE b.property.id = :propertyId " +
            "AND :endDate > b.startDate " +
            "AND :startDate < b.endDate")
    List<Booking> findBookingsForPropertyAtDate(UUID propertyId, LocalDateTime startDate, LocalDateTime endDate);


    */
    List<Booking> findByDeletedFalse();
}