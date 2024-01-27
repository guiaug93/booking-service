package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Booking;
import com.hostfully.bookingservice.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking create(Booking booking) {
        //TODO verificar se o mesmo guest ja nao reservou a mesma propriedade
        /*if (bookingRepository.findByDocument(booking.getDocument()) != null) {
            throw new ServiceException("There is already a booking with that document");
        }*/
        //verificar se data esta disponivel
        //verificar se nao ha bloqueios
        return bookingRepository.save(booking);
    }

    private boolean isDateAvailable(Booking newBooking, List<Booking> bookingHistory) {
        for (Booking existingBooking : bookingHistory) {
            if (newBooking.getStartDate().isBefore(existingBooking.getEndDate()) &&
                    newBooking.getEndDate().isAfter(existingBooking.getStartDate())) {
                return false;
            }
        }
        return true;
    }

    public Booking update(UUID id, Booking updatedBooking) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);

        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            booking.setStartDate(updatedBooking.getStartDate());
            booking.setEndDate(updatedBooking.getEndDate());
            booking.setGuest(updatedBooking.getGuest());

            //verificar se data esta disponivel
            //verificar se nao ha bloqueios

            return bookingRepository.save(booking);
        } else {
            throw new ServiceException("Booking not found");
        }
    }

    public Booking rebook(UUID id, Booking rebook){
        Optional<Booking> existingBooking = bookingRepository.findById(id);

        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            if(booking.getDeleted()){
                throw new ServiceException("Booking is deleted");
            }
            if(booking.getStatus()!= Booking.BookingStatus.CANCELED){
                throw new ServiceException("Booking is not canceled");
            }

            //TODO verificar se a data esta disponivel
            booking.setStartDate(rebook.getStartDate());
            booking.setEndDate(rebook.getEndDate());
            //TODO SALVAR NO HISTORICO
            return bookingRepository.save(booking);
        } else {
            throw new ServiceException("Booking not found");
        }
    }

    public Booking cancelBooking(UUID id){

        //TODO
        return null;
    }


    public Booking getById(UUID id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElse(null);
    }

    public List<Booking> fetchAll() {
        return bookingRepository.findByDeletedFalse();
    }

    public void delete(UUID id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent()) {
            Booking deletedBooking = booking.get();
            deletedBooking.setDeleted(true);
            bookingRepository.save(deletedBooking);
        } else {
            throw new ServiceException("Booking not found");
        }
    }
}