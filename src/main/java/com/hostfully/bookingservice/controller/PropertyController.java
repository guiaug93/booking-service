package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.model.Property;
import com.hostfully.bookingservice.service.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyService.create(property);
    }


    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.fetchAll();
    }

    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable UUID id) {
        return propertyService.getById(id);
    }

    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable UUID id, @RequestBody Property property) {
        return propertyService.update(id, property);
    }

    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable UUID id) {
        propertyService.delete(id);
    }
}