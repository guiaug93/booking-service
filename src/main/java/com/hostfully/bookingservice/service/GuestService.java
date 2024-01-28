package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Guest;
import com.hostfully.bookingservice.repository.GuestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest create(Guest guest) {
        if (guestRepository.findByDocument(guest.getDocument()) != null) {
            throw new ServiceException("There is already a guest with that document", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return guestRepository.save(guest);
    }

    public Guest update(UUID id, Guest updatedGuest) {
        Optional<Guest> existingGuest = guestRepository.findById(id);

        if (existingGuest.isPresent()) {
            Guest guest = existingGuest.get();
            guest.setName(updatedGuest.getName());
            guest.setDocument(updatedGuest.getDocument());
            guest.setAddress(updatedGuest.getAddress());
            guest.setTelephone(updatedGuest.getTelephone());
            guest.setMail(updatedGuest.getMail());

            return guestRepository.save(guest);
        } else {
            throw new ServiceException("Guest not found", HttpStatus.NOT_FOUND);
        }
    }

    public Guest getById(UUID id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Guest not found", HttpStatus.NOT_FOUND));
    }

    public List<Guest> fetchAll() {
        return guestRepository.findByDeletedFalse();
    }

    public void delete(UUID id) {
        Optional<Guest> guest = guestRepository.findById(id);
        if (guest.isPresent()) {
            Guest deletedGuest = guest.get();
            deletedGuest.setDeleted(true);
            guestRepository.save(deletedGuest);
        } else {
            throw new ServiceException("Guest not found", HttpStatus.NOT_FOUND);
        }
    }
}