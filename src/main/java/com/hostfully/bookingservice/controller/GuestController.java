package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.service.GuestService;
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
    public Guest createGuest(@RequestBody Guest guest) {
        return guestService.create(guest);
    }


    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.fetchAll();
    }

    @GetMapping("/{id}")
    public Guest getGuestById(@PathVariable UUID id) {
        return guestService.getById(id);
    }

    @PutMapping("/{id}")
    public Guest updateGuest(@PathVariable UUID id, @RequestBody Guest guest) {
        return guestService.update(id, guest);
    }

    @DeleteMapping("/{id}")
    public void deleteGuest(@PathVariable UUID id) {
        guestService.delete(id);
    }
}