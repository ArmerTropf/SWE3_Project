package de.hsb.webapp.bc.controller;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
	 * 
	 */
	private static final long serialVersionUID = 2449443003535807049L;

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
}
