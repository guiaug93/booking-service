package com.hostfully.bookingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "\"property\"")
public class Property {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnoreProperties("properties")
    private Owner owner;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private BigDecimal dailyValue;

    private BigDecimal cleaningValue;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

    public Property(){

    }

    public Property(UUID id, Owner owner, String name, String description, BookingStatus status, LocalDateTime createdAt, BigDecimal dailyValue, BigDecimal cleaningValue, Boolean deleted) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.dailyValue = dailyValue;
        this.cleaningValue = cleaningValue;
        this.deleted = deleted;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getDailyValue() {
        return dailyValue;
    }

    public void setDailyValue(BigDecimal dailyValue) {
        this.dailyValue = dailyValue;
    }

    public BigDecimal getCleaningValue() {
        return cleaningValue;
    }

    public void setCleaningValue(BigDecimal cleaningValue) {
        this.cleaningValue = cleaningValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public enum BookingStatus {
        AVAILABLE, UNAVAILABLE
    }
}