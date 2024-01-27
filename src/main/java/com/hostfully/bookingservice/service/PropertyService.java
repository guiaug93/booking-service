package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Property;
import com.hostfully.bookingservice.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Property create(Property property) {
        if (propertyRepository.findByName(property.getName()) != null) {
            throw new ServiceException("There is already a property with that name");
        }
        return propertyRepository.save(property);
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
            throw new ServiceException("Property not found");
        }
    }

    public Property getById(UUID id) {
        Optional<Property> property = propertyRepository.findById(id);
        return property.orElse(null);
    }

    public List<Property> fetchAll() {
        return propertyRepository.findByDeletedFalse();
    }

    public void delete(UUID id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isPresent()) {
            Property deletedProperty = property.get();
            deletedProperty.setDeleted(true);
            propertyRepository.save(deletedProperty);
        } else {
            throw new ServiceException("Property not found");
        }
    }
}