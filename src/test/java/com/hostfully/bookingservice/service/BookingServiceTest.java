package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Booking;
import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.model.Property;
import com.hostfully.bookingservice.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private GuestService guestService;

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success() {
        UUID guestId = UUID.randomUUID();
        Guest guest = new Guest();
        guest.setId(guestId);
        guest.setDeleted(false);

        UUID ownerId = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setDeleted(false);

        Property property = new Property();
        property.setId(UUID.randomUUID());
        property.setOwner(owner);
        property.setName("Property 1");
        property.setDeleted(false);

        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(2);

        Booking newBooking = new Booking();
        newBooking.setProperty(property);
        newBooking.setStartDate(startDate);
        newBooking.setEndDate(endDate);
        newBooking.setGuest(guest);

        when(propertyService.getById(property.getId())).thenReturn(property);
        when(guestService.getById(guestId)).thenReturn(guest);
        when(bookingRepository.findBookingsForPropertyAtDate(property.getId(), startDate, endDate)).thenReturn(new ArrayList<>());
        when(bookingRepository.save(any(Booking.class))).thenReturn(newBooking);

        assertDoesNotThrow(() -> bookingService.create(newBooking, Booking.BookingType.GUEST_BOOKING));

        verify(propertyService, times(1)).getById(property.getId());
        verify(guestService, times(1)).getById(guestId);
        verify(bookingRepository, times(1)).findBookingsForPropertyAtDate(property.getId(), startDate, endDate);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCreateBooking_OverlappingBooking() {
        UUID guestId = UUID.randomUUID();
        Guest guest = new Guest();
        guest.setId(guestId);
        guest.setDeleted(false);

        UUID ownerId = UUID.randomUUID();
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setDeleted(false);

        Property property = new Property();
        property.setId(UUID.randomUUID());
        property.setOwner(owner);
        property.setName("Property 1");
        property.setDeleted(false);

        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(2);

        Booking existingBooking = new Booking();
        existingBooking.setProperty(property);
        existingBooking.setStartDate(startDate);
        existingBooking.setEndDate(endDate);
        existingBooking.setGuest(guest);

        Booking newBooking = new Booking();
        newBooking.setProperty(property);
        newBooking.setStartDate(startDate);
        newBooking.setEndDate(endDate);
        newBooking.setGuest(guest);

        List<Booking> existingBookings = new ArrayList<>();
        existingBookings.add(existingBooking);

        when(propertyService.getById(property.getId())).thenReturn(property);
        when(guestService.getById(guestId)).thenReturn(guest);
        when(bookingRepository.findBookingsForPropertyAtDate(property.getId(), startDate, endDate)).thenReturn(existingBookings);

        assertThrows(ServiceException.class, () -> bookingService.create(newBooking, Booking.BookingType.GUEST_BOOKING));

        verify(propertyService, times(1)).getById(property.getId());
        verify(guestService, times(1)).getById(guestId);
        verify(bookingRepository, times(1)).findBookingsForPropertyAtDate(property.getId(), startDate, endDate);
        verify(bookingRepository, never()).save(any(Booking.class));
    }
}