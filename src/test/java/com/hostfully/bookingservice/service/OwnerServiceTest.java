package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.model.Property;
import com.hostfully.bookingservice.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOwnerSuccess() {
        UUID ownerId = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setDeleted(false);

        when(ownerRepository.findByDocument(owner.getDocument())).thenReturn(null);
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        Owner savedOwner = ownerService.create(owner);

        assertNotNull(savedOwner);
        assertEquals(owner.getName(), savedOwner.getName());
        assertEquals(owner.getDocument(), savedOwner.getDocument());
        assertFalse(savedOwner.isDeleted());
        verify(ownerRepository, times(1)).findByDocument(owner.getDocument());
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    void testCreateOwnerDuplicateDocument() {
        UUID ownerId = UUID.randomUUID();
        Owner existingOwner = new Owner();
        existingOwner.setId(ownerId);
        existingOwner.setDeleted(false);

        when(ownerRepository.findByDocument(existingOwner.getDocument())).thenReturn(existingOwner);

        assertThrows(ServiceException.class, () -> ownerService.create(existingOwner));

        verify(ownerRepository, times(1)).findByDocument(existingOwner.getDocument());
        verify(ownerRepository, never()).save(any(Owner.class));
    }

    @Test
    void testDeleteOwnerAndProperties() {
        UUID ownerId = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setDeleted(false);

        Property property1 = new Property();
        property1.setId(UUID.randomUUID());
        property1.setOwner(owner);
        property1.setDeleted(false);

        Property property2 = new Property();
        property2.setId(UUID.randomUUID());
        property2.setOwner(owner);
        property2.setDeleted(false);

        owner.setProperties(Arrays.asList(property1, property2));

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(ownerRepository.save(any(Owner.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> ownerService.delete(ownerId));

        assertTrue(owner.isDeleted());
        assertTrue(property1.isDeleted());
        assertTrue(property2.isDeleted());

        verify(ownerRepository, times(1)).findById(ownerId);
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    void testDeleteNonExistingOwner() {
        UUID ownerId = UUID.randomUUID();

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> ownerService.delete(ownerId));

        verify(ownerRepository, times(1)).findById(ownerId);
        verify(ownerRepository, never()).save(any(Owner.class));
    }
}