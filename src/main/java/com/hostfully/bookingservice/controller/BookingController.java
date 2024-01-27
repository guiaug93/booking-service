package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.model.Booking;
import com.hostfully.bookingservice.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.create(booking);
    }


    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.fetchAll();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable UUID id) {
        return bookingService.getById(id);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable UUID id, @RequestBody Booking booking) {
        return bookingService.update(id, booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable UUID id) {
        bookingService.delete(id);
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelBooking(@PathVariable UUID id) {
        bookingService.cancelBooking(id);
    }

    @PatchMapping("/{id}")
    public Booking rebook(@PathVariable UUID id, @RequestBody Booking booking) {
        return bookingService.rebook(id, booking);
    }
}