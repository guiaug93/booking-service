package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.model.Booking;
import com.hostfully.bookingservice.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/booking-block")
public class BookingBlockController {

    private final BookingService bookingService;

    public BookingBlockController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking createBlock(@RequestBody Booking block) {
        return bookingService.create(block, Booking.BookingType.MAINTENANCE);
    }


    @GetMapping
    public List<Booking> getAllBlocks() {
        return bookingService.fetchAll(Booking.BookingType.MAINTENANCE);
    }

    @GetMapping("/{id}")
    public Booking getBlockById(@PathVariable UUID id) {
        return bookingService.getById(id);
    }

    @PutMapping("/{id}")
    public Booking updateBlock(@PathVariable UUID id, @RequestBody Booking booking) {
        return bookingService.update(id, booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBlock(@PathVariable UUID id) {
        bookingService.delete(id);
    }

}