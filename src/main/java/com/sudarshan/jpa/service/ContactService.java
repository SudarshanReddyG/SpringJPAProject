package com.sudarshan.jpa.service;

import java.util.List;

import com.sudarshan.jpa.dto.ContactDTO;
import com.sudarshan.jpa.dto.SearchDTO;
import com.sudarshan.jpa.exception.NotFoundException;
import com.sudarshan.jpa.model.Contact;

public interface ContactService {
	
	public Contact add(ContactDTO added);
	
	public Contact deleteById(int id) throws NotFoundException;
	
	public List<Contact> findAll();
	
	public Contact findById(int id) throws NotFoundException;
	
	public Contact update(ContactDTO updated) throws NotFoundException;
	
	public List<Contact> search(String searchItem);
	
	public List<Contact> searchNamedQuery(String searchItem);
	
	public List<Contact> searchQueryAnnt(String searchItem);
	
	public List<Contact> searchQueryCriteria(String searchItem);
	
	public List<Contact> searchDslQuery(String searchItem);

	List<Contact> findAllWithSort();

	List<Contact> searchQueryCriteriaWithSort(String searchItem);

	List<Contact> searchDslQueryWithOrder(String searchItem);
	
	public List<Contact> findAllForPage(int pageIndex, int pageSize);

	public List<Contact> search(SearchDTO dto);
}
