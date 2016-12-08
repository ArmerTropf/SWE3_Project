package de.hsb.webapp.bc.controller;

import de.hsb.webapp.bc.model.*;

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
public class UserAndShelvesHandler  {


	
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
	 * User Datamodel to store userdata.
	 */
	private DataModel<User> user;
	public DataModel<User> getUser() {
		return user;
	}

	/**
	 * Sets the user into datamodel.
	 * 
	 * @param DataModel<User> user
	 *            Set datamodel for user.
	 */
	public void setUser(DataModel<User> user) {
		this.user = user;
	}
	
	/**
	 * Sets the user into datamodel.
	 */
	private User rememberUser = new User();
	
	/**
	 * Gets the rememberdUser object.
	 * @return
	 * Current rememberUser.
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
	
	/**
	 * Init data for first use.
	 */
	@PostConstruct
	public void init() 
	{
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}


		em.persist(new User("Guenster", "Hans","12345","mguenster",true));
		em.persist(new User("Guenster", "Albert","12345","mguenster",true));
		em.persist(new User("Guenster", "Michael","12345","mguenster",false));
		
		user = new ListDataModel<User>();
		user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
		
		try {
			utx.commit();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (RollbackException e) {
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		System.out.println("INIT UserAndShelves done");
	}
	
	/**
	 * Prepare remeberUser for first use. initialize with userobject.
	 * @return
	 * String "addUser" which is the redirect to addUser.xhtml.
	 */
	public String newUser() 
	{
		rememberUser = new User();
		System.out.println("newUser done");
		return "addUser";
	}
	
	/**
	 * Deletes rememberUser from Entitymanager an set it into user (Datamodel).
	 * @return
	 * String "userAdministration" which is the redirect to userAdministration.xhtml.
	 */
	public String deleteUser()
	{
		rememberUser = user.getRowData();
		// -> Transaktionsbeginn
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rememberUser = em.merge(rememberUser);
		em.remove(rememberUser);
		user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());

		// -> Transaktionsende
		try {
			utx.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("deleteUser done");
		
		return "userAdministration";
	}
	
	/**
	 * Saves rememberUser from Entitymanager an set it into user (Datamodel).
	 * @return
	 * String "userAdministration" which is the redirect to userAdministration.xhtml.
	 */
	public String saveUser() 
	{
			
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rememberUser = em.merge(rememberUser);
		em.persist(rememberUser);
		user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
		
		try {
			utx.commit();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("saveUser done");
		return "userAdministration";
	}
	
	/**
	 * Edit rememberUser to edit
	 * @return
	 * String "userAdministration" which is the redirect to userAdministration.xhtml
	 */
	public String editUser() 
	{
		rememberUser = user.getRowData();
		System.out.println("editUser done");
		return "addUser";
	}
	
	public String cancelUser()
	{
		System.out.println("cancelUser done");
		return "userAdministration";
	}
	


}
