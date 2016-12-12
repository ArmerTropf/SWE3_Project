package de.hsb.webapp.bc.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;

/**
 * With this class you can create authors.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@SuppressWarnings("serial")
@NamedQuery(name = "SelectAuthor", query = "Select a from Author a")
@Entity
public class Author implements Serializable {

	// Start ---Declaration of variables---

	/**
	 * Author ID is the primary key for a author. A UUID will be generated
	 * automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer aid;

	/**
	 * First name of the author.
	 */
	@Size(min = 2, max = 30)
	private String firstname;

	/**
	 * Last name of the author.
	 */
	@Size(min = 2, max = 30)
	private String lastname;

	// End ---Declaration of variables---

	/**
	 * Empty constructor.
	 */
	public Author() {
		super();
	}

	/**
	 * Constructor using fields to create an author.
	 * 
	 * @param firstname
	 *            Author's first name.
	 * @param lastname
	 *            Author's last name.
	 */
	public Author(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		
	}

	// Start ---Getter & Setter---

	/**
	 * Gets the first name of the author.
	 * 
	 * @return Author's first name.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the first name of the author.
	 * 
	 * @param firstname
	 *            Define new first name.
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets the last name of the author.
	 * 
	 * @return Author's last name.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the last name of the author.
	 * 
	 * @param lastname
	 *            Define new last name.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	// End ---Getter & Setter---
	
		
}
