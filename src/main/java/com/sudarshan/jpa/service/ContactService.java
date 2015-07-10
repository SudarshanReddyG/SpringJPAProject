package com.sudarshan.jpa.service;

import java.util.List;

import com.sudarshan.jpa.dto.ContactDTO;
import com.sudarshan.jpa.exception.NotFoundException;
import com.sudarshan.jpa.model.Contact;

public interface ContactService {
	
	public Contact add(ContactDTO added);
	
	public Contact deleteById(Long id) throws NotFoundException;
	
	public List<Contact> findAll();
	
	public Contact findById(Long id) throws NotFoundException;
	
	public Contact update(ContactDTO updated) throws NotFoundException;

}
