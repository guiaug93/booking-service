package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(description = "Create a owner")
    public Owner createOwner(@RequestBody Owner owner) {
        return ownerService.create(owner);
    }


    @GetMapping
    @Operation(description = "Get all owners")
    public List<Owner> getAllOwners() {
        return ownerService.fetchAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Get owner by id")
    public Owner getOwnerById(@PathVariable UUID id) {
        return ownerService.getById(id);
    }

    @PutMapping("/{id}")
    @Operation(description = "Update a owner")
    public Owner updateOwner(@PathVariable UUID id, @RequestBody Owner owner) {
        return ownerService.update(id, owner);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a owner")
    public void deleteOwner(@PathVariable UUID id) {
        ownerService.delete(id);
    }
}