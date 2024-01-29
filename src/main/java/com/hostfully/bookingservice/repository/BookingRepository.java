package com.hostfully.bookingservice.repository;

import com.hostfully.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    @Query("SELECT b FROM Booking b " +
            "WHERE b.property.id = :propertyId " +
            "AND :endDate > b.startDate " +
            "AND :startDate < b.endDate " +
            "AND b.status = 'BOOKED'" +
            "AND b.deleted = false")
    List<Booking> findBookingsForPropertyAtDate(@Param("propertyId") UUID propertyId,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.deleted = false AND b.bookingType = :bookingType")
    List<Booking> findAllNotDeletedByBookingType(@Param("bookingType") Booking.BookingType bookingType);
}