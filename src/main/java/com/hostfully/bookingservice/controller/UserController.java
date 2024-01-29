package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.exception.ServiceException;
import com.hostfully.bookingservice.model.User;
import com.hostfully.bookingservice.service.UserService;
import com.hostfully.bookingservice.utils.ValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(
            description = "Create Users Service"
    )
    public User createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ServiceException("Validation error: " + ValidationUtils.processFieldErrors(bindingResult), HttpStatus.BAD_REQUEST);
        }
        return userService.create(user);
    }


    @GetMapping
    @Operation(description = "Get All Users")
    public List<User> getAllUsers() {
        return userService.fetchAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Get user by id")
    public User getUserById(@PathVariable UUID id) {
        return userService.getById(id);
    }


    @PutMapping("/{id}")
    @Operation(description = "Update user")
    public User updateUser(@PathVariable UUID id, @RequestBody User updatedUser) {
        return userService.update(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete a User")
    public void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }
}