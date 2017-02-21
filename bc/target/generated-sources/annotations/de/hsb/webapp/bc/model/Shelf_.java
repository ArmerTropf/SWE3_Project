package de.hsb.webapp.bc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Shelf.class)
public abstract class Shelf_ {

	public static volatile ListAttribute<Shelf, Book> books;
	public static volatile SingularAttribute<Shelf, String> name;
	public static volatile SingularAttribute<Shelf, User> user;
	public static volatile SingularAttribute<Shelf, Integer> sid;

}

