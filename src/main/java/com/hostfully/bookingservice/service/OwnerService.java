package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.Owner;
import com.hostfully.bookingservice.repository.OwnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner create(Owner owner) {
        if (ownerRepository.findByDocument(owner.getDocument()) != null) {
            throw new ServiceException("There is already a owner with that document", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return ownerRepository.save(owner);
    }

    public Owner update(UUID id, Owner updatedOwner) {
        Optional<Owner> existingOwner = ownerRepository.findById(id);

        if (existingOwner.isPresent()) {
            Owner owner = existingOwner.get();
            owner.setName(updatedOwner.getName());
            owner.setDocument(updatedOwner.getDocument());
            owner.setAddress(updatedOwner.getAddress());
            owner.setTelephone(updatedOwner.getTelephone());
            owner.setMail(updatedOwner.getMail());

            return ownerRepository.save(owner);
        } else {
            throw new ServiceException("Owner not found", HttpStatus.NOT_FOUND);
        }
    }

    public Owner getById(UUID id) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);

        Owner owner = ownerOptional.orElseThrow(() -> new ServiceException("Owner not found", HttpStatus.NOT_FOUND));

        if (owner.isDeleted()) {
            throw new ServiceException("Owner is deleted", HttpStatus.NOT_FOUND);
        }

        return owner;
    }

    public List<Owner> fetchAll() {
        return ownerRepository.findByDeletedFalse();
    }

    public void delete(UUID id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        if (owner.isPresent()) {
            Owner deletedOwner = owner.get();
            deletedOwner.setDeleted(true);
            ownerRepository.save(deletedOwner);
        } else {
            throw new ServiceException("Owner not found", HttpStatus.NOT_FOUND);
        }
    }
}