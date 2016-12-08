package de.hsb.webapp.bc.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 * With this class you can create users and admins.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@SuppressWarnings("serial")
@NamedQuery(name = "SelectUser", query = "Select u from User u")
@Entity
public class User implements Serializable {

	// Start ---Declaration of variables---

	/**
	 * User ID is the primary key for a user. A UUID will be generated
	 * automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer uid;

	/**
	 * Last name of the user.
	 */
	@Size(min = 2, max = 30)
	private String lastname;

	/**
	 * First name of the user.
	 */
	@Size(min = 2, max = 30)
	private String firstname;

	/**
	 * User's password.
	 */
	private String password;

	/**
	 * User's login name.
	 */
	private String login;

	/**
	 * isAdmin says if the user is an administrator or not. The default value is
	 * "false".
	 */
	private boolean isAdmin = false;
	
	/**
	 * isActivated says whether the user account is activated for login.
	 * Default value is "false".
	 */
	private boolean isActivated = false;

	/**
	 * A user may have many shelves. They will be stored in a collection.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Shelf> shelves;

	// End ---Declaration of variables---

	/**
	 * Empty constructor. Every user will get a default shelf called
	 * "Mein Hauptregal".
	 */
	public User() {
		super();
		this.shelves = new Vector<Shelf>();
		this.shelves.add(new Shelf("Mein Hauptregal"));
	}

	/**
	 * Constructor using fields to create a user with a default shelf called
	 * "Mein Hauptregal".
	 * 
	 * @param lastname
	 *            Last name of user.
	 * @param firstname
	 *            First name of user.
	 * @param password
	 *            password of user.
	 * @param login
	 *            Login name of user.
	 * @param isActivated Says if the user account is activated.           
	 */
	public User(String lastname, String firstname, String password, String login, boolean isActivated) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.login = login;
		this.isActivated = isActivated;
	}

	/**
	 * Constructor using fields to create a user with a collection of shelves.
	 * 
	 * @param lastname
	 *            Last name of user.
	 * @param firstname
	 *            First name of user.
	 * @param password
	 *            Password of user.
	 * @param login
	 *            Login name of user.
	 * @param shelves
	 *            Shelves of user.
	 * @param isActivated Says if the user account is activated.  
	 */
	public User(String lastname, String firstname, String password, String login, Collection<Shelf> shelves, boolean isActivated) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.login = login;
		this.shelves = shelves;
		this.isActivated = isActivated;
	}

	/**
	 * Constructor using fields to create an administrator with a default shelf
	 * called "Mein Hauptregal"
	 * 
	 * @param lastname
	 *            Last name of user.
	 * @param firstname
	 *            First name of user.
	 * @param password
	 *            Password of user.
	 * @param login
	 *            Login name of user.
	 * @param isAdmin
	 *            Says if user is an administrator.
	 * @param isActivated Says if the user account is activated.
	 */
	public User(String lastname, String firstname, String password, String login, Boolean isAdmin, boolean isActivated) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.login = login;
		this.isAdmin = isAdmin;
		this.isActivated = isActivated;
		this.shelves = new Vector<Shelf>();
		this.shelves.add(new Shelf("Mein Hauptregal"));
	}

	/**
	 * Constructor using fields to create an administrator with a collection of
	 * shelves.
	 * 
	 * @param lastname
	 *            Last name of user.
	 * @param firstname
	 *            First name of user.
	 * @param password
	 *            Password of user.
	 * @param login
	 *            Login name of user.
	 * @param isAdmin
	 *            Says if user is an administrator.
	 * @param shelves
	 *            Shelves of user.
	 * @param isActivated Says if the user account is activated.           
	 */
	public User(String lastname, String firstname, String password, String login, boolean isAdmin,
			Collection<Shelf> shelves, boolean isActivated) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.login = login;
		this.isAdmin = isAdmin;
		this.shelves = shelves;
	}

	// Start ---Getter & Setter---

	/**
	 * Gets the last name of the user.
	 * 
	 * @return Current last name.
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Sets the last name of the user.
	 * 
	 * @param lastname
	 *            Define new last name.
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gets the first name of the user.
	 * 
	 * @return Current first name.
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Sets the first name of the user.
	 * 
	 * @param firstname
	 *            Define new first name.
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gets the user password.
	 * 
	 * @return Current user password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the user.
	 * 
	 * @param password
	 *            Define new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the user login.
	 * 
	 * @return Current login name.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Sets the login name of the user.
	 * 
	 * @param login
	 *            Define new login name.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets information if user is an administrator.
	 * 
	 * @return True if user is an administrator.
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * Sets the flag if user is an administrator.
	 * 
	 * @param isAdmin
	 *            True for setting user to an administrator.
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * Gets the shelves of the user.
	 * 
	 * @return Current shelves.
	 */
	public Collection<Shelf> getShelves() {
		return shelves;
	}

	/**
	 * Sets the shelves of the user.
	 * 
	 * @param shelves
	 *            Define new shelves.
	 */
	public void setShelves(Collection<Shelf> shelves) {
		this.shelves = shelves;
	}
	
	/**
	 * Gets the isActivated of the user.
	 * 
	 * @return Current state of activation of the user account.
	 */
	public boolean isActivated()	{
		return this.isActivated;
	}
	
	
	/**
	 * Sets the isActive of the user.
	 * 
	 * @param shelves
	 *            True for activating the user account.
	 */
	public void setActivated(boolean isActivated)	{
		this.isActivated = isActivated;
	}

	// End ---Getter & Setter---
}
