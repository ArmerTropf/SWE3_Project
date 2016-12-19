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
 * Function to Check the Password and Username are not implementet yet
 * 
 *
 */
/**
 * With this class you will handle the login
 * 
 * ****Function to Check the Password and Username are not implementet yet****
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@SessionScoped
@ManagedBean(name = "userLoginHandler")
public class UserLogin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7189330607628509074L;

	/**
	 * Entity Manger for the user and shelves.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * User transactions for the user and shelves.
	 */
	@Resource
	private UserTransaction utx;

	private String username;

	private String password;

	User loggedInUser = new User();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@PostConstruct
	private void init() {
		try {
			utx.begin();
			em.persist(new User("Guenster", "Michael", "12345", "mguenster", true, true));
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String login() {
		FacesMessage message = null;

		System.out.println(this.username);
		System.out.println(this.password);

		try {
			utx.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Query query = em.createNamedQuery("User.findByName").setParameter("login", this.username)
				.setParameter("password", this.password);

		// Laesst beim abmelden eine erneute anmeldung zu
		query.setMaxResults(1);

		if (query.getResultList().isEmpty()) {
			System.out.println("Name oder passord falsch nutte");

			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
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

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String logout() {
		//FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login.jsf?faces-redirect=true";
	}

}