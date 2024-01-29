package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.service.GuestService;
import com.hostfully.bookingservice.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
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
    public Guest createGuest(@RequestBody @Valid Guest guest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ServiceException("Validation error: " + ValidationUtils.processFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
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