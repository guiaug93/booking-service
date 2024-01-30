package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.model.Property;
import com.hostfully.bookingservice.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    private final OwnerService ownerService;

    public PropertyService(PropertyRepository propertyRepository, OwnerService ownerService) {
        this.propertyRepository = propertyRepository;
        this.ownerService = ownerService;
    }

    public Property create(Property property) {
        try {
            if (propertyRepository.findByName(property.getName()) != null) {
                throw new ServiceException("There is already a property with that name", HttpStatus.NOT_MODIFIED);
            }

            checkOwner(property.getOwner().getId());

            property.setCreatedAt(LocalDateTime.now());
            property.setStatus(Property.BookingStatus.AVAILABLE);
            property.setDeleted(false);
            return propertyRepository.save(property);
        } catch (Exception e) {
            throw new ServiceException("Failed to create property: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void checkOwner(UUID id){
        Owner owner = ownerService.getById(id);
        if(owner.isDeleted()){
            throw new ServiceException("Owner is deleted", HttpStatus.NOT_FOUND);
        }
    }

    public Property update(UUID id, Property updatedProperty) {
        Optional<Property> existingProperty = propertyRepository.findById(id);

        if (existingProperty.isPresent()) {
            Property property = existingProperty.get();
            property.setOwner(updatedProperty.getOwner());
            property.setName(updatedProperty.getName());
            property.setDescription(updatedProperty.getDescription());
            property.setDailyValue(updatedProperty.getDailyValue());
            property.setCleaningValue(updatedProperty.getCleaningValue());
            property.setUpdatedAt(LocalDateTime.now());

            return propertyRepository.save(property);
        } else {
            throw new ServiceException("Property not found", HttpStatus.NOT_FOUND);
        }
    }

    public Property getById(UUID id) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);

        Property property = propertyOptional.orElseThrow(() -> new ServiceException("Property not found", HttpStatus.NOT_FOUND));

        if (property.isDeleted()) {
            throw new ServiceException("Property is deleted", HttpStatus.NOT_FOUND);
        }

        return property;
    }

    public List<Property> fetchAll() {
        return propertyRepository.findByDeletedFalse();
    }

    public void delete(UUID id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isPresent()) {
            Property deletedProperty = property.get();
            if(deletedProperty.isDeleted()){
                throw new ServiceException("Property is already deleted", HttpStatus.NOT_MODIFIED);
            }
            deletedProperty.setDeleted(true);
            propertyRepository.save(deletedProperty);
        } else {
            throw new ServiceException("Property not found", HttpStatus.NOT_FOUND);
        }
    }
}