package com.sudarshan.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.sudarshan.jpa.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>, JpaSpecificationExecutor<Contact>, QueryDslPredicateExecutor<Contact>{
	
	public List<Contact> findByFirstNameStartingWithOrLastNameStartingWith(String firstName, String lastName);
	
	public List<Contact> findByFirstNameStartingWithOrLastNameStartingWithOrderByLastNameAscFirstNameAsc(String firstName, String lastName);
	
	public List<Contact> findByFirstNameStartingWithOrLastNameStartingWithOrderByLastNameAscFirstNameAsc(String firstName, String lastName, Pageable pageable);
	
	public List<Contact> findByFirstNameStartingWithOrLastNameStartingWith(String firstName, String lastName, Sort sort);
	
	public List<Contact> showContacts(@Param("searchTerm") String searchTerm); //we canot use sort here
	
	@Query("SELECT c FROM Contact c WHERE LOWER(c.firstName) LIKE LOWER(:searchTerm) OR LOWER(c.lastName) LIKE LOWER(:searchTerm) ORDER BY c.lastName ASC, c.firstName ASC")
	public List<Contact> searchFirstNameOrLastNameWithSort(@Param("searchTerm") String searchTerm);
	
	@Query("SELECT c FROM Contact c WHERE LOWER(c.firstName) LIKE LOWER(:searchTerm) OR LOWER(c.lastName) LIKE LOWER(:searchTerm)")
	public List<Contact> searchFirstNameOrLastName(@Param("searchTerm") String searchTerm);
	
	@Query("SELECT c FROM Contact c WHERE LOWER(c.firstName) LIKE LOWER(:searchTerm) OR LOWER(c.lastName) LIKE LOWER(:searchTerm)")
	public List<Contact> searchFirstNameOrLastNameWithSortObj(@Param("searchTerm") String searchTerm, Sort sort);
	
	
}
