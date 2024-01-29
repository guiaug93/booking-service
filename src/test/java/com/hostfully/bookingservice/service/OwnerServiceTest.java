package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Owner;
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
    void testCreateOwner_Success() {
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
    void testCreateOwner_DuplicateDocument() {
        UUID ownerId = UUID.randomUUID();
        Owner existingOwner = new Owner();
        existingOwner.setId(ownerId);
        existingOwner.setDeleted(false);

        when(ownerRepository.findByDocument(existingOwner.getDocument())).thenReturn(existingOwner);

        assertThrows(ServiceException.class, () -> ownerService.create(existingOwner));

        verify(ownerRepository, times(1)).findByDocument(existingOwner.getDocument());
        verify(ownerRepository, never()).save(any(Owner.class));
    }

}