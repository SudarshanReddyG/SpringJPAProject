package com.sudarshan.jpa.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Contact.class)
public class Contact_ {
	
	public static volatile SingularAttribute<Contact, String> firstName;
	
	public static volatile SingularAttribute<Contact, String> lastName;
}
