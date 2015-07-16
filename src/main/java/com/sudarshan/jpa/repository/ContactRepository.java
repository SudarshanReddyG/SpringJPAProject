package com.sudarshan.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudarshan.jpa.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
