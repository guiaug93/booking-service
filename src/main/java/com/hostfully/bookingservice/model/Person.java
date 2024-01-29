package com.hostfully.bookingservice.model;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String document;

    private String telephone;

    private String address;

    private String mail;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

    public Person() { }


    public Person(UUID id, String name, String document, String telephone, String address, String mail, Boolean deleted) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.telephone = telephone;
        this.address = address;
        this.mail = mail;
        this.deleted = deleted;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
