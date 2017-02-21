package de.hsb.webapp.bc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Integer> uid;
	public static volatile SingularAttribute<User, String> firstname;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Boolean> isAdmin;
	public static volatile SingularAttribute<User, Boolean> isActivated;
	public static volatile SingularAttribute<User, String> login;
	public static volatile ListAttribute<User, Shelf> shelves;
	public static volatile SingularAttribute<User, String> lastname;
	public static volatile SingularAttribute<User, String> myTheme;

}

