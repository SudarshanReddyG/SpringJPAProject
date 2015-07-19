package com.sudarshan.jpa.model.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.sudarshan.jpa.model.Contact;
import com.sudarshan.jpa.model.Contact_;

public class ContactSpecification {

	public static Specification<Contact> firstOrLastNameStartWith(final String searchTerm) {
		return new Specification<Contact>() {

			@Override
			public Predicate toPredicate(Root<Contact> root, CriteriaQuery<?> cq,
					CriteriaBuilder cb) {
				String likePattern = getLikePattern(searchTerm);
				return cb.or(
			                  cb.like(cb.lower(root.<String>get(Contact_.firstName)), likePattern),
			                  cb.like(cb.lower(root.<String>get(Contact_.lastName)), likePattern)
			                );
			}

			private String getLikePattern(final String searchTerm) {
				return searchTerm.toLowerCase() + "%";
			}
		};
	}


}
