package com.hostfully.bookingservice.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "\"users\"")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String password;

    private String mail;

    private String name;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

    public User(UUID id, String password, String mail, String name, Boolean deleted) {
        this.id = id;
        this.password = password;
        this.mail = mail;
        this.name = name;
        this.deleted = deleted;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}