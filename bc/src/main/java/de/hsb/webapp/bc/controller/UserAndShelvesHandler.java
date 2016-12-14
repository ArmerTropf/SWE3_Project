package de.hsb.webapp.bc.controller;

import de.hsb.webapp.bc.model.*;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	
	/**
	 * LoggedIn user.
	 */
	private DataModel<User> loggedInUser;

	/**
	 * Remembers the current user.
	 */
	private User rememberUser = new User();

	/**
	 * Initializes the data model with some user for the first use.
	 */
	
	private Shelf rememberShelf = new Shelf();
	
	private String username;
	private String password;
	
	@PostConstruct
	public void init() {
		try {
			utx.begin();
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		em.persist(new User("Guenster", "Hans", "12345", "mguenster", true));
		em.persist(new User("Guenster", "Albert", "12345", "aschriever", true));
		em.persist(new User("Guenster", "Michael", "12345", "hmahrt", false));

		user = new ListDataModel<User>();
		user.setWrappedData(em.createNamedQuery("SelectUser").getResultList());
		
		loggedInUser = new ListDataModel<User>();

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
	 * 
	 * @return String "addUser" which is the redirect to addUser.xhtml.
	 */
	public String newUser() {
		rememberUser = new User();
		System.out.println("newUser done");
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
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		rememberUser = em.merge(rememberUser);
		em.remove(rememberUser);
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
		System.out.println("deleteUser done");
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
		} catch (NotSupportedException e) {
			e.printStackTrace();
		} catch (SystemException e) {
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
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml
	 */
	public String editUser() {
		rememberUser = user.getRowData();
		System.out.println("editUser done");
		return "addUser";
	}

	/**
	 * Cancel adding or editing process of a user account.
	 * 
	 * @return String "userAdministration" which is the redirect to
	 *         userAdministration.xhtml
	 */
	public String cancelUser() {
		System.out.println("cancelUser done");
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

	
	public String logout()
	{
		rememberUser = null;
		return "login";
	}
	
		
    public String showUser()
    {
    	return "userAdministration";
    }
	
    
    /**
     * @return
     */
    public String login() 
    {
    	FacesMessage message = null;
    	
    	System.out.println(this.username);
    	System.out.println(this.password);
    		
      try {
			utx.begin();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	  	Query query = em.createNamedQuery("User.findByName")
	  			.setParameter("login", this.username)
	  			.setParameter("password", this.password);
     	
	  	if(query.getResultList().isEmpty())
	  	{
	  		System.out.println("Name oder passord falsch nutte");
	  		message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
	  		FacesContext.getCurrentInstance().addMessage(null, message);
	  		
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
	  		
	  		
	  		return "login";
	  	}
	  	else 
	  	{
	  		User hit = new User();
	  	  	hit = (User) query.getSingleResult();
	  	  	System.out.println(hit.getLogin());
	  	  	this.rememberUser = hit;
	  	  	
	  	  	rememberUser.getShelves().add(new Shelf("HOMOHSHELF"));
//	  	  	rememberUser.getShelves().get(0).getBooks().add(new Book("MEin", "11234", new Author(), "haare", "keins"));
//	  	  	rememberUser = em.merge(rememberUser);
//	  	  	em.persist(rememberUser);
	  	  	System.out.println("in login");
	  	  	
	  	  	message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", this.username);
	  	  	FacesContext.getCurrentInstance().addMessage(null, message);
	  	  	
	  	  	
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
	  	  	
	  	  	
	  	  
	  	  	return "showOwnLibrary";
	  	}
  
    } 
    
    public void homo()
    {
    	System.out.println("HAHAHSAA");
    }
  
   

  

}
