package de.hsb.webapp.bc.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import de.hsb.webapp.bc.model.User;

/**
 * With this class you will handle the user login.
 * 
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@SessionScoped
@ManagedBean(name = "userLoginHandler")
public class UserLogin implements Serializable {

	// Start ---Declaration of variables---

	/**
	 * "The serialization runtime associates with each serializable class a
	 * version number, called a serialVersionUID, which is used during
	 * deserialization to verify that the sender and receiver of a serialized
	 * object have loaded classes for that object that are compatible with
	 * respect to serialization" -
	 * https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	 */
	private static final long serialVersionUID = -7189330607628509074L;

	/**
	 * Entity Manger for the user.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * User transactions for the user.
	 */
	@Resource
	private UserTransaction utx;

	/**
	 * The user name from the login window.
	 */
	private String username;

	/**
	 * The user password from the login window.
	 */
	private String password;

	/**
	 * The current user logged in.
	 */
	private User loggedInUser = new User();

	// End ---Declaration of variables---

	/**
	 * Initializise the database with an administrator user.
	 */
	@PostConstruct
	private void init() {
		try {
			utx.begin();
			em.persist(new User("User", "Super", "12345", "root", true, true));
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logs in the user.
	 * 
	 * @return String "" which is the main page of the webapp, if the
	 *         credentials are correct, otherwise user will stay at the login
	 *         page.
	 */
	public String login() {
		FacesMessage message = null;
		try {
			utx.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = em.createNamedQuery("User.findByName").setParameter("login", this.username)
				.setParameter("password", this.password);
		query.setMaxResults(1); // allows a new login process for the same user
								// after he logs out
		if (query.getResultList().isEmpty()) {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login error", "Invalid credentials");
			FacesContext.getCurrentInstance().addMessage(null, message);
			try {
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "login";
		} else {
			loggedInUser = (User) query.getSingleResult();
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", this.username);
			FacesContext.getCurrentInstance().addMessage(null, message);
			try {
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "showOwnLibrary";
		}
	}

	/**
	 * Logs out the user.
	 * 
	 * @return String "login" which is the login page.
	 */
	public String logout() {
		// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login";
	}

	// Start ---Getter & Setter---

	/**
	 * Gets the current user logged in.
	 * 
	 * @return Current user.
	 */
	public User getLoggedInUser() {
		return loggedInUser;
	}

	/**
	 * Sets the current user.
	 * 
	 * @param loggedInUser
	 *            New user.
	 */
	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	/**
	 * Gets the user name from the login window.
	 * 
	 * @return User name.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user name in the login window.
	 * 
	 * @param username
	 *            New user name.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the user password from the login window.
	 * 
	 * @return User password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user password from the login window.
	 * 
	 * @param password
	 *            New user password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	// End ---Getter & Setter---
}