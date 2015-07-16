package com.sudarshan.jpa.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sudarshan.jpa.dto.ContactDTO;
import com.sudarshan.jpa.exception.NotFoundException;
import com.sudarshan.jpa.model.Contact;
import com.sudarshan.jpa.repository.ContactRepository;

@Service
public class RepositoryContactService implements ContactService {

	@Resource
	private ContactRepository repository;
	
	@Transactional
	@Override
	public Contact add(ContactDTO added) {
		Contact contact = Contact.getBuilder(added.getFirstName(), added.getLastName())
				.address(added.getStreetAddress(), added.getPostCode(), added.getPostOffice(), added.getState(), added.getCountry())
				.emailAddress(added.getEmailAddress())
				.phoneNumber(added.getPhoneNumber())
				.build();

		return repository.save(contact);
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<Contact> findAll() {
		return repository.findAll();
	}
	
	@Transactional(rollbackFor = NotFoundException.class)
	@Override
	public Contact deleteById(int id) throws NotFoundException {
		
		Contact contact = findById(id);
		
		repository.delete(contact);
		
		return contact;
	}
	
	@Transactional(readOnly=true)
	@Override
	public Contact findById(int id) throws NotFoundException {
		System.out.println("id is "+id+" "+repository);
		Contact contact1 = null;
		try {
			contact1 = repository.findOne(id);
		} catch(Exception exp) {
			exp.printStackTrace();
		}
		System.out.println("contact is "+contact1);
		if(contact1 == null) {
			throw new NotFoundException("No contact found with id: " + id);
		}
		return contact1;
	}
	
	@Transactional(rollbackFor = NotFoundException.class)
	@Override
	public Contact update(ContactDTO updated) throws NotFoundException {
		
		Contact found = findById(updated.getId());

        //Update the contact information
        found.update(updated.getFirstName(), updated.getLastName(), updated.getEmailAddress(), updated.getPhoneNumber());
        //Update the address information
        found.updateAddress(updated.getStreetAddress(), updated.getPostCode(), updated.getPostOffice(), updated.getState(), updated.getCountry());
        
		return found;
	}

}
