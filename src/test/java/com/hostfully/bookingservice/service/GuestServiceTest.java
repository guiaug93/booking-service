package com.hostfully.bookingservice.service;
import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateGuest_Success() {
        Guest guest = new Guest(UUID.randomUUID(), "John Doe", "123456789", "Address", "1234567890", "john@example.com", false);

        when(guestRepository.findByDocument(guest.getDocument())).thenReturn(null);
        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        Guest savedGuest = guestService.create(guest);

        assertNotNull(savedGuest);
        assertEquals(guest.getName(), savedGuest.getName());
        assertEquals(guest.getDocument(), savedGuest.getDocument());
        assertFalse(savedGuest.isDeleted());
        verify(guestRepository, times(1)).findByDocument(guest.getDocument());
        verify(guestRepository, times(1)).save(any(Guest.class));
    }

    @Test
    void testCreateGuest_DuplicateDocument() {
        Guest existingGuest = new Guest(UUID.randomUUID(), "Jane Doe", "123456789", "Address", "1234567890", "jane@example.com", false);

        when(guestRepository.findByDocument(existingGuest.getDocument())).thenReturn(existingGuest);

        assertThrows(ServiceException.class, () -> guestService.create(existingGuest));

        verify(guestRepository, times(1)).findByDocument(existingGuest.getDocument());
        verify(guestRepository, never()).save(any(Guest.class));
    }
}