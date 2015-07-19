package com.sudarshan.jpa.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.OrderSpecifier;
import com.sudarshan.jpa.dto.ContactDTO;
import com.sudarshan.jpa.dto.SearchDTO;
import com.sudarshan.jpa.exception.NotFoundException;
import com.sudarshan.jpa.model.Contact;
import com.sudarshan.jpa.model.QContact;
import com.sudarshan.jpa.model.dsl.ContactPredicates;
import com.sudarshan.jpa.model.specification.ContactSpecification;
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

	@Transactional(readOnly=true)
	@Override
	public List<Contact> findAllWithSort() {
		return repository.findAll(sortByFirstNameAndLastName());
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

	@Transactional(readOnly=true)
	@Override
	public List<Contact> search(String searchItem) {
		return repository.findByFirstNameStartingWithOrLastNameStartingWithOrderByLastNameAscFirstNameAsc(searchItem, searchItem);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Contact> searchNamedQuery(String searchItem) {
		String likePattern = buildLikePattern(searchItem);
		return repository.showContacts(likePattern);
	}

	private String buildLikePattern(String searchTerm) {
		return searchTerm + "%";
	}

	@Transactional(readOnly=true)
	@Override
	public List<Contact> searchQueryAnnt(String searchItem) {
		String likePattern = buildLikePattern(searchItem);
		return repository.searchFirstNameOrLastName(likePattern);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Contact> searchQueryCriteria(String searchItem) {
		Specification<Contact> specification = ContactSpecification.firstOrLastNameStartWith(searchItem);
		return repository.findAll(specification);
	}

	@Transactional(readOnly=true)
	@Override
	public List<Contact> searchQueryCriteriaWithSort(String searchItem) {
		Specification<Contact> specification = ContactSpecification.firstOrLastNameStartWith(searchItem);
		return repository.findAll(specification, sortByFirstNameAndLastName());
	}

	@Override
	public List<Contact> searchDslQuery(String searchItem) {
		Predicate predicate = ContactPredicates.firstOrLastNameStartsWith(searchItem);
		List<Contact> contactsList = new ArrayList<Contact>();
		//repository.findAll(predicate);
		return null;
	}
	
	@Override
	public List<Contact> searchDslQueryWithOrder(String searchItem) {
		Predicate predicate = ContactPredicates.firstOrLastNameStartsWith(searchItem);
		List<Contact> contactsList = new ArrayList<Contact>();
		//repository.findAll(predicate, sortByLastNameAndFirstNameAsc());
		return null;
	}

	private Sort sortByFirstNameAndLastName() {
		return new Sort(new Sort.Order(Direction.ASC, "lastName"), new Sort.Order(Direction.ASC, "firstName"));
	}

	private OrderSpecifier[] sortByLastNameAndFirstNameAsc() {
		OrderSpecifier[] orders = {QContact.contact.lastName.asc(), QContact.contact.firstName.asc()};
		return orders;
	}

	@Override
	public List<Contact> findAllForPage(int pageIndex, int pageSize) {
		Page<Contact> page = repository.findAll(buildPageSpecification(pageIndex, pageSize));
		return page.getContent();
	}

	@Override
	public List<Contact> search(SearchDTO dto) {
		return repository.findByFirstNameStartingWithOrLastNameStartingWithOrderByLastNameAscFirstNameAsc(dto.getSearchTerm(), dto.getSearchTerm(), buildPageSpecification(dto.getPageIndex(), dto.getPageSize()));
	}
	
	private Pageable buildPageSpecification(int pageIndex, int pageSize) {
		return new PageRequest(pageIndex, pageSize, sortByFirstNameAndLastName());
	}
}
