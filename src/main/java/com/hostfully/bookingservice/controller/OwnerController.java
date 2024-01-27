package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.service.OwnerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    public Owner createOwner(@RequestBody Owner owner) {
        return ownerService.create(owner);
    }


    @GetMapping
    public List<Owner> getAllOwners() {
        return ownerService.fetchAll();
    }

    @GetMapping("/{id}")
    public Owner getOwnerById(@PathVariable UUID id) {
        return ownerService.getById(id);
    }

    @PutMapping("/{id}")
    public Owner updateOwner(@PathVariable UUID id, @RequestBody Owner owner) {
        return ownerService.update(id, owner);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable UUID id) {
        ownerService.delete(id);
    }
}