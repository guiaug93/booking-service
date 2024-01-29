package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.User;
import com.hostfully.bookingservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        if (userRepository.findByMail(user.getMail()) != null) {
            throw new ServiceException("Mail is already in use", HttpStatus.NOT_FOUND);
        }
        user.setPassword(user.getPassword());
        user.setDeleted(false);
        return userRepository.save(user);
    }

    public User update(UUID userId, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            user.setName(updatedUser.getName());
            user.setMail(updatedUser.getMail());

            return userRepository.save(user);
        } else {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
        }
    }

    public User getById(UUID id) {
        Optional<User> userOptional = userRepository.findById(id);

        User user = userOptional.orElseThrow(() -> new ServiceException("User not found", HttpStatus.NOT_FOUND));

        if (user.isDeleted()) {
            throw new ServiceException("User is deleted", HttpStatus.NOT_FOUND);
        }

        return user;
    }

    public List<User> fetchAll() {
        return userRepository.findAll();

    }

    public void delete(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User deletedGuest = user.get();
            deletedGuest.setDeleted(true);
            userRepository.save(deletedGuest);
        } else {
            throw new ServiceException("User not found", HttpStatus.NOT_FOUND);
        }
    }
}