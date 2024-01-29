package com.hostfully.bookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "\"booking\"")
public class Booking {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
    @NotBlank
    private LocalDateTime startDate;
    @NotBlank
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private BookingType bookingType;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

    public Booking(){

    }

    public Booking(UUID id, BookingStatus status, Property property, LocalDateTime startDate, LocalDateTime endDate, BookingType bookingType, Guest guest, Boolean deleted) {
        this.id = id;
        this.status = status;
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingType = bookingType;
        this.guest = guest;
        this.deleted = deleted;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public enum BookingType {
        GUEST_BOOKING, MAINTENANCE
    }

    public enum BookingStatus {
        BOOKED, CANCELED
    }
}