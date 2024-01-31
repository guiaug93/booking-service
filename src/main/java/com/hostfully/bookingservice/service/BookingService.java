package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Booking;
import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.model.Property;
import com.hostfully.bookingservice.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final GuestService guestService;
    private final PropertyService propertyService;

    public BookingService(BookingRepository bookingRepository, GuestService guestService, PropertyService propertyService) {
        this.bookingRepository = bookingRepository;
        this.guestService = guestService;
        this.propertyService = propertyService;
    }

    public Booking create(Booking booking, Booking.BookingType bookingType) {
        if(bookingType == Booking.BookingType.GUEST_BOOKING){
            checkGuest(booking.getGuest().getId());
        }

        checkProperty(booking.getProperty().getId());
        checkBookingAvailability(booking);
        booking.setBookingType(bookingType);
        booking.setStatus(Booking.BookingStatus.BOOKED);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setDeleted(false);
        return bookingRepository.save(booking);
    }

    private void checkGuest(UUID id){
        Guest guest = guestService.getById(id);
        if(guest.isDeleted()){
            throw new ServiceException("Guest is deleted", HttpStatus.NOT_FOUND);
        }
    }

    private void checkProperty(UUID id){
        Property property = propertyService.getById(id);
        if(property.isDeleted()){
            throw new ServiceException("Property is deleted", HttpStatus.NOT_FOUND);
        }
    }

    private void checkBookingAvailability(Booking newBooking) {
        List<Booking> bookingHistory = bookingRepository.findBookingsForPropertyAtDate(newBooking.getProperty().getId(), newBooking.getStartDate(), newBooking.getEndDate());
        for (Booking existingBooking : bookingHistory) {
            checkBlocks(existingBooking);
            checkDateAvailability(newBooking, existingBooking);
        }
    }

    private void checkBlocks(Booking booking){
        if(booking.getBookingType() == Booking.BookingType.MAINTENANCE){
            throw new ServiceException("Booking is blocked due to maintenance", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private void checkDateAvailability(Booking newBooking, Booking booking){
        if (newBooking.getStartDate().isBefore(booking.getEndDate()) &&
                newBooking.getEndDate().isAfter(booking.getStartDate())) {
            throw new ServiceException("Range of dates not available for booking", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public Booking update(UUID id, Booking updatedBooking) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);

        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            booking.setStartDate(updatedBooking.getStartDate());
            booking.setEndDate(updatedBooking.getEndDate());
            if(booking.getBookingType() == Booking.BookingType.GUEST_BOOKING){
                booking.setGuest(updatedBooking.getGuest());
            }
            if(booking.getStatus() == Booking.BookingStatus.CANCELED){
                throw new ServiceException("This booking is cancelled, it`s necessary to rebook!", HttpStatus.UNPROCESSABLE_ENTITY);
            }

            checkBookingAvailability(booking);

            return bookingRepository.save(booking);
        } else {
            throw new ServiceException("Booking not found", HttpStatus.NOT_FOUND);
        }
    }

    public Booking rebook(UUID id, Booking rebook){
        Optional<Booking> existingBooking = bookingRepository.findById(id);

        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            validateBookingUpdate(booking);

            booking.setStartDate(rebook.getStartDate());
            booking.setEndDate(rebook.getEndDate());
            booking.setStatus(Booking.BookingStatus.BOOKED);
            checkBookingAvailability(booking);
            return bookingRepository.save(booking);
        } else {
            throw new ServiceException("Booking not found", HttpStatus.NOT_FOUND);
        }
    }

    private void validateBookingUpdate(Booking booking){
        if(booking.isDeleted()){
            throw new ServiceException("Booking is deleted", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(booking.getStatus() != Booking.BookingStatus.CANCELED){
            throw new ServiceException("Booking is not canceled", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(booking.getBookingType() != Booking.BookingType.GUEST_BOOKING){
            throw new ServiceException("Not allowed to rebook a blocked booking, please delete it", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void cancelBooking(UUID id){
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            booking.setStatus(Booking.BookingStatus.CANCELED);

            bookingRepository.save(booking);
        } else {
            throw new ServiceException("Booking not found", HttpStatus.NOT_FOUND);
        }
    }


    public Booking getById(UUID id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);

        Booking booking = bookingOptional.orElseThrow(() -> new ServiceException("Booking not found", HttpStatus.NOT_FOUND));

        if (booking.isDeleted()) {
            throw new ServiceException("Booking is deleted", HttpStatus.NOT_FOUND);
        }

        return booking;
    }


    public List<Booking> fetchAll(Booking.BookingType bookingType) {
        return bookingRepository.findAllNotDeletedByBookingType(bookingType);
    }

    public void delete(UUID id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Booking deletedBooking = booking.get();
            if(deletedBooking.isDeleted()){
                throw new ServiceException("Booking is already deleted", HttpStatus.UNPROCESSABLE_ENTITY);
            }
            deletedBooking.setDeleted(true);
            bookingRepository.save(deletedBooking);
        } else {
            throw new ServiceException("Booking not found", HttpStatus.NOT_FOUND);
        }
    }
}