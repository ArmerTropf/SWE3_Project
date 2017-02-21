package de.hsb.webapp.bc.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

/**
 * With this class you can create users and admins.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@NamedQueries({ @NamedQuery(name = "SelectUser", query = "Select u from User u"),
		@NamedQuery(name = "SelectUserLogins", query = "Select l from User l where l.login = :login"),
		@NamedQuery(name = "User.findByName", query = "SELECT c FROM User c WHERE c.login = :login AND c.password = :password"), })
@Entity
public class User implements Serializable {

	// Start ---Declaration of variables---

	/**
	 * "The serialization runtime associates with each serializable class a
	 * version number, called a serialVersionUID, which is used during
	 * deserialization to verify that the sender and receiver of a serialized
	 * object have loaded classes for that object that are compatible with
	 * respect to serialization" -
	 * https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	 */
	private static final long serialVersionUID = -3499625914266174872L;

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
	@Size(min = 5, max = 10)
	private String password;

	/**
	 * User's login name.
	 */
	@Size(min = 2, max = 10)
	private String login;

	/**
	 * isAdmin says if the user is an administrator or not. The default value is
	 * "false".
	 */
	private boolean isAdmin = false;

	/**
	 * isActivated says whether the user account is activated for login. Default
	 * value is "false".
	 */
	private boolean isActivated = true;

	/**
	 * A user may have many shelves. They will be stored in a List.
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private List<Shelf> shelves;

	/**
	 * Current theme for the user.
	 */
	private String myTheme = "delta";

	// End ---Declaration of variables---

	/**
	 * Empty constructor.
	 */
	public User() {
		super();
	}

	/**
	 * Constructor using fields to create a user.
	 * 
	 * @param lastname
	 *            Last name of user.
	 * @param firstname
	 *            First name of user.
	 * @param password
	 *            password of user.
	 * @param login
	 *            Login name of user.
	 * @param isActivated
	 *            Says if the user account is activated.
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
	 * Constructor using fields to create an administrator.
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
	 * @param isActivated
	 *            Says if the user account is activated.
	 */
	public User(String lastname, String firstname, String password, String login, boolean isAdmin,
			boolean isActivated) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.login = login;
		this.isAdmin = isAdmin;
		this.isActivated = isActivated;
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
	public List<Shelf> getShelves() {
		return shelves;
	}

	/**
	 * Sets the shelves of the user.
	 * 
	 * @param shelves
	 *            Define new shelves.
	 */
	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
	}

	/**
	 * Gets the information if the user is activated for login.
	 * 
	 * @return Current state of activation of the user account.
	 */
	public boolean isActivated() {
		return this.isActivated;
	}

	/**
	 * Sets the isActive of the user.
	 * 
	 * @param shelves
	 *            True for activating the user account.
	 */
	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	/**
	 * Gets the ID of the user.
	 * 
	 * @return User's ID.
	 */
	public Integer getUid() {
		return uid;
	}

	/**
	 * Sets the ID of the user.
	 * 
	 * @param uid
	 *            New ID for the user.
	 */
	public void setUid(Integer uid) {
		this.uid = uid;
	}

	/**
	 * Gets the current theme.
	 * 
	 * @return String of the theme.
	 */
	public String getMyTheme() {
		return myTheme;
	}

	/**
	 * Sets a new theme for the user.
	 * 
	 * @param myTheme
	 *            String for theme.
	 */
	public void setMyTheme(String myTheme) {
		this.myTheme = myTheme;
	}

	// End ---Getter & Setter---
}
