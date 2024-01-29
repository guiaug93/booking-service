package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/guest")
public class GuestController {

    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PostMapping
    @Operation(description = "Create a guest")
    public Guest createGuest(@RequestBody Guest guest) {
        return guestService.create(guest);
    }


    @GetMapping
    @Operation(description = "Get all guests")
    public List<Guest> getAllGuests() {
        return guestService.fetchAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Get guest by id")
    public Guest getGuestById(@PathVariable UUID id) {
        return guestService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a guest")
    public Guest updateGuest(@PathVariable UUID id, @RequestBody Guest guest) {
        return guestService.update(id, guest);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a guest")
    public void deleteGuest(@PathVariable UUID id) {
        guestService.delete(id);
    }
}