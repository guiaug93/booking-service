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
@RequestMapping("/api/booking-block")
public class BookingBlockController {

    private final BookingService bookingService;

    public BookingBlockController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation(description = "Create a block")
    public Booking createBlock(@RequestBody @Valid Booking block, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ServiceException("Validation error: " + ValidationUtils.processFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        return bookingService.create(block, Booking.BookingType.MAINTENANCE);
    }

    @GetMapping
    @Operation(description = "Get all blocks")
    public List<Booking> getAllBlocks() {
        return bookingService.fetchAll(Booking.BookingType.MAINTENANCE);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get block by id")
    public Booking getBlockById(@PathVariable UUID id) {
        return bookingService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a block")
    public Booking updateBlock(@PathVariable UUID id, @RequestBody Booking booking) {
        return bookingService.update(id, booking);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a block")
    public void deleteBlock(@PathVariable UUID id) {
        bookingService.delete(id);
    }

}