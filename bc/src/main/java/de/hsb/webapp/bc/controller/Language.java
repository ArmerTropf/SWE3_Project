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
@ManagedBean(name="language")
@SessionScoped
public class Language implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void changeLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
    }
}