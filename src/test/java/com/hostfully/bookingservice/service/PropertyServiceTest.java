package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.model.Property;
import com.hostfully.bookingservice.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private PropertyService propertyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePropertySuccess() {
        UUID ownerId = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setDeleted(false);

        Property property = new Property();
        property.setId(UUID.randomUUID());
        property.setOwner(owner);
        property.setName("Property 1");

        when(propertyRepository.findByName("Property 1")).thenReturn(null);
        when(ownerService.getById(ownerId)).thenReturn(owner);
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        Property savedProperty = propertyService.create(property);

        assertNotNull(savedProperty);
        assertEquals(property.getName(), savedProperty.getName());
        assertEquals(property.getDescription(), savedProperty.getDescription());
        assertEquals(property.getOwner(), savedProperty.getOwner());
        assertEquals(property.getDailyValue(), savedProperty.getDailyValue());
        assertEquals(property.getCleaningValue(), savedProperty.getCleaningValue());
        assertEquals(Property.BookingStatus.AVAILABLE, savedProperty.getStatus());
        assertFalse(savedProperty.isDeleted());
        verify(propertyRepository, times(1)).findByName("Property 1");
        verify(ownerService, times(1)).getById(ownerId);
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void testCreatePropertyDuplicateName() {
        UUID ownerId = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setDeleted(false);

        Property existingProperty = new Property();
        existingProperty.setId(UUID.randomUUID());
        existingProperty.setOwner(owner);
        existingProperty.setName("Property 1");

        when(propertyRepository.findByName("Property 1")).thenReturn(existingProperty);

        assertThrows(ServiceException.class, () -> propertyService.create(existingProperty));

        verify(propertyRepository, times(1)).findByName("Property 1");
        verify(ownerService, never()).getById(any(UUID.class));
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testUpdateNonExistingProperty() {
        UUID propertyId = UUID.randomUUID();
        Property updatedProperty = new Property();
        updatedProperty.setId(propertyId);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> propertyService.update(propertyId, updatedProperty));

        verify(propertyRepository, times(1)).findById(propertyId);
        verify(propertyRepository, never()).save(any(Property.class));
    }

    @Test
    void testGetDeletedPropertyById() {
        UUID propertyId = UUID.randomUUID();
        Property deletedProperty = new Property();
        deletedProperty.setId(propertyId);
        deletedProperty.setDeleted(true);

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(deletedProperty));

        assertThrows(ServiceException.class, () -> propertyService.getById(propertyId));

        verify(propertyRepository, times(1)).findById(propertyId);
    }

    @Test
    void testDeleteNonExistingProperty() {
        UUID propertyId = UUID.randomUUID();

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> propertyService.delete(propertyId));

        verify(propertyRepository, times(1)).findById(propertyId);
        verify(propertyRepository, never()).save(any(Property.class));
    }

}