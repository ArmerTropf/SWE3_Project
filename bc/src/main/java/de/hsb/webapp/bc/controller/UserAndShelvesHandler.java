package de.hsb.webapp.bc.controller;

import de.hsb.webapp.bc.model.*;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * With this class you will handle the user and shelves actions.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */

@ManagedBean(name = "userAndShelvesHandler")
@SessionScoped
public class UserAndShelvesHandler implements Serializable {

	/**
	 * "The serialization runtime associates with each serializable class a
	 * version number, called a serialVersionUID, which is used during
	 * deserialization to verify that the sender and receiver of a serialized
	 * object have loaded classes for that object that are compatible with
	 * respect to serialization" -
	 * https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	 */
	private static final long serialVersionUID = -6973592033673957562L;

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

	/**
	 * Stores all user.
	 */
	private DataModel<User> user;

	private DataModel<Shelf> shelves;

	/**
	 * Remembers the current user.
	 */
	private User rememberUser = new User();

	private Shelf rememberShelf = new Shelf();

	/**
	 * Initializes the data model with some user for the first use.
	 */
	@PostConstruct
	public void init() {
		try {
			utx.begin();
			em.persist(new User("Guenster", "Hans", "12345", "mguenster", true));
			user = new ListDataModel<User>();
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			shelves = new ListDataModel<Shelf>();
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String newShelf(){
		rememberUser = user.getRowData();
		rememberShelf = new Shelf();
		return "XHTML Page for creating new shelf";
	}
	
	public String editShelf(){
		rememberUser = user.getRowData();
		rememberShelf = shelves.getRowData();
		return "XHTML Page for editing shelf";
	}
	
	public String saveShelf(){
		try {
			utx.begin();
			rememberShelf = em.merge(rememberShelf);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "XHTML Page with shelves";
	}
	
	public String deleteShelf(){
		rememberUser=user.getRowData();
		rememberShelf=shelves.getRowData();
		rememberUser.getShelves().remove(rememberShelf);
		
		try {
			utx.begin();
			rememberShelf = em.merge(rememberShelf);
			em.remove(rememberShelf);
			rememberUser=em.merge(rememberUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "XHTML Page with shelves";
	}
	
	/**
	 * Prepare remeberUser for first use. initialize with userobject.
	 * 
	 * @return String "addUser" which is the redirect to addUser.xhtml.
	 */
	public String newUser() {
		rememberUser = new User();
		return "addUser";
	}

	/**
	 * Deletes rememberUser from Entitymanager an set it into user (Datamodel).
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml.
	 */
	public String deleteUser() {
		rememberUser = user.getRowData();
		try {
			utx.begin();
			rememberUser = em.merge(rememberUser);
			em.remove(rememberUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
			shelves.setWrappedData(em.createNamedQuery("SelectShelf").getResultList());
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userAdministration";
	}

	/**
	 * Saves rememberUser from entity manager an set it into user (datamodel).
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml.
	 */
	public String saveUser() {
		try {
			utx.begin();
			rememberUser = em.merge(rememberUser);
			user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userAdministration";
	}

	/**
	 * Edit rememberUser to edit
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml
	 */
	public String editUser() {
		rememberUser = user.getRowData();
		return "addUser";
	}

	/**
	 * Cancel adding or editing process of a user account.
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml
	 */
	public String cancelUser() {
		return "userAdministration";
	}

	/**
	 * Gets the current userlsit (datamodel)
	 * 
	 * @return datamodel with users.
	 */
	public DataModel<User> getUser() {
		return user;
	}

	/**
	 * Sets the user into datamodel.
	 * 
	 * @param DataModel<User>
	 *            user Set datamodel for user.
	 */
	public void setUser(DataModel<User> user) {
		this.user = user;
	}

	/**
	 * Gets the rememberdUser object.
	 * 
	 * @return Current rememberUser.
	 */
	public User getrememberUser() {
		return rememberUser;
	}

	/**
	 * Sets the rememberUser.
	 */
	public void setrememberUser(User rememberUser) {
		this.rememberUser = rememberUser;
	}
}
