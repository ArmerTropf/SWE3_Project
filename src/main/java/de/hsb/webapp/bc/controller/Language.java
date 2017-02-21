package de.hsb.webapp.bc.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * With this class you will handle the internationalization.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@ManagedBean(name = "language")
@SessionScoped
public class Language implements Serializable {

	/**
	 * "The serialization runtime associates with each serializable class a
	 * version number, called a serialVersionUID, which is used during
	 * deserialization to verify that the sender and receiver of a serialized
	 * object have loaded classes for that object that are compatible with
	 * respect to serialization" -
	 * https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stores the locale information.
	 */
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

	/**
	 * Changes the current language in the webapp interface.
	 * 
	 * @param language
	 *            Language to change to.
	 */
	public void changeLanguage(String language) {
		locale = new Locale(language);
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
	}

	/**
	 * Gets the locale.
	 * 
	 * @return Current locale.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Gets the current language.
	 * 
	 * @return Current language.
	 */
	public String getLanguage() {
		return locale.getLanguage();
	}
}