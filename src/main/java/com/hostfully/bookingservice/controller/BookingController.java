package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Booking;
import com.hostfully.bookingservice.service.BookingService;
import com.hostfully.bookingservice.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
    @Operation(description = "Create a booking")
    public Booking createBooking(@RequestBody @Valid Booking booking, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ServiceException("Validation error: " + ValidationUtils.processFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        return bookingService.create(booking, Booking.BookingType.GUEST_BOOKING);
    }


    @GetMapping
    @Operation(description = "Get all bookings")
    public List<Booking> getAllBookings() {
        return bookingService.fetchAll(Booking.BookingType.GUEST_BOOKING);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get booking by id")
    public Booking getBookingById(@PathVariable UUID id) {
        return bookingService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a booking")
    public Booking updateBooking(@PathVariable UUID id, @RequestBody Booking booking) {
        return bookingService.update(id, booking);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a booking")
    public void deleteBooking(@PathVariable UUID id) {
        bookingService.delete(id);
    }

    @DeleteMapping("/cancel/{id}")
    @Operation(description = "Cancel a booking")
    public void cancelBooking(@PathVariable UUID id) {
        bookingService.cancelBooking(id);
    }

    @PatchMapping("/rebook/{id}")
    @Operation(description = "Rebook a canceled booking")
    public Booking rebook(@PathVariable UUID id, @RequestBody Booking booking) {
        return bookingService.rebook(id, booking);
    }
}