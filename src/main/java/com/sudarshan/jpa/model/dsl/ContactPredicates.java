package com.sudarshan.jpa.model.dsl;

import javax.persistence.criteria.Predicate;

import com.sudarshan.jpa.model.QContact;

public class ContactPredicates {

	public static Predicate firstOrLastNameStartsWith(final String searchTerm) {
		QContact contact = QContact.contact;
		return (Predicate) contact.firstName.startsWithIgnoreCase(searchTerm).or(contact.lastName.startsWithIgnoreCase(searchTerm));
	}

}
