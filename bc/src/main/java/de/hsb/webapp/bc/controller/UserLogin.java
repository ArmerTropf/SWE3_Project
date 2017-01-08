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
	 * Initializise the database with an administrator an guest user.
	 */
	@PostConstruct
	private void init() {
		try {
			utx.begin();
			em.persist(new User("User", "Super", "12345", "root", true, true));
			em.persist(new User("User", "Guest", "67890", "guest", true));
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logs in the user.
	 * 
	 * @return String "mainSite" which is the main page of the webapp, if the
	 *         credentials are correct, otherwise user will stay at the login
	 *         page.
	 */
	public String login(String titel, String message) {
		FacesMessage msg = null;
		try {
			utx.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = em.createNamedQuery("User.findByName").setParameter("login", this.username)
				.setParameter("password", this.password);
		query.setMaxResults(1); // allows a new login process for the same user
								// after he logs out
		if (query.getResultList().isEmpty()) { // no credentials match with
												// database
			msg = new FacesMessage(FacesMessage.SEVERITY_WARN, titel, message);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			try {
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "login";
		} else if (!(loggedInUser = (User) query.getSingleResult()).isActivated()) { // user
																						// is
																						// not
																						// activated
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Your account is not activated!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			try {
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "login";
		} else {
			try {
				utx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "mainSite";
		}
	}

	/**
	 * Logs out the user and saves the current theme.
	 * 
	 * @return String "login" which is the login page.
	 */
	public String logout() {
		try {
			utx.begin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		loggedInUser = em.merge(loggedInUser);
		try {
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// loggedInUser = new User();
		// Invoke "PostConstruct"
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login?faces-redirect=true";

	}

	/**
	 * Provides that a non logged in user can visit a website by typing the URL
	 * to a xhtml page directly into the browser. And provides that a user can
	 * visit the user management page and provides that an admin can visit the
	 * user profile page.
	 */
	public void checkUserLogin() {
		FacesContext context = FacesContext.getCurrentInstance();
		String xhtmlPage = context.getExternalContext().getRequestServletPath();
		if (loggedInUser.getLogin() == null) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"login?faces-redirect=true");
		} else if (xhtmlPage.equals("/showUser.jsf") && !loggedInUser.isAdmin()) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"mainSite?faces-redirect=true");
		} else if (xhtmlPage.equals("/userProfile.jsf") && loggedInUser.isAdmin()) {
			context.getApplication().getNavigationHandler().handleNavigation(context, null,
					"mainSite?faces-redirect=true");
		}
	}

	/**
	 * Changes the theme and redirects to the current site.
	 * 
	 * @param newTheme
	 *            Theme to change to.
	 * @param whichSiteToRedirect
	 *            Site to redirect.
	 * @return String which is the redirect of the site.
	 */
	public String changeTheme(String newTheme, String whichSiteToRedirect) {
		this.loggedInUser.setMyTheme(newTheme);
		return whichSiteToRedirect + "?faces-redirect=true";
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