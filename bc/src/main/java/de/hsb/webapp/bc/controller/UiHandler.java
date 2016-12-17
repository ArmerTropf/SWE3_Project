package de.hsb.webapp.bc.controller;


import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import de.hsb.webapp.bc.model.User;

@ManagedBean(name = "uiHandler")
@SessionScoped
public class UiHandler 
{
	User rememberUser = new User();
	String lastUI;
	String showShelfs = "showShelfs";
	String showBooks = "showBooksOverview";
	String whichLayout;
	String whichPage;
	
	Map<String,String> country;
	
	String uiSouth = "showShelfs";
	String uiCenter = "showBooksOverview";
	
	public String getUiCenter() {
		return uiCenter;
	}
	public String getUiCenter(String uiCenter) {
		
		this.uiCenter = uiCenter;
		return "showOwnLibrary.jsf?faces-redirect=true";
	}
	public String getUiSouth() {
		return uiSouth;
	}
	public String UiSouth(String uiSouth) {
		this.uiSouth = uiSouth;
		
		 
		return "showOwnLibrary.jsf?faces-redirect=true";
	}
	
	public String outcome(){

		FacesContext fc = FacesContext.getCurrentInstance();
		this.country = getCountryParam(fc);

		this.whichLayout = this.country.get("whichLayout");
		this.whichPage = this.country.get("country");
		
		System.out.println("Case ist:" + this.whichLayout);
		
		switch (this.whichLayout) {
		case "south":
			this.uiSouth = this.whichPage;
			break;
		case "center":
			this.uiCenter = this.whichPage;
			break;


		default:
			break;
		}
		
		System.out.println("uiSouth:" + this.uiSouth);
		System.out.println("uiCenter:" + this.uiCenter);
		
		return "showOwnLibrary.jsf?faces-redirect=true";
	}
	
	public String outcome(User rememberedUser){

		this.rememberUser = rememberedUser;
		
		
		FacesContext fc = FacesContext.getCurrentInstance();
		this.country = getCountryParam(fc);

		this.whichLayout = this.country.get("whichLayout");
		this.whichPage = this.country.get("country");
		
		System.out.println("Case ist:" + this.whichLayout);
		
		switch (this.whichLayout) {
		case "south":
			this.uiSouth = this.whichPage;
			break;
		case "center":
			this.uiCenter = this.whichPage;
			break;


		default:
			break;
		}
		
		System.out.println("uiSouth:" + this.uiSouth);
		System.out.println("uiCenter:" + this.uiCenter);
		
		return "showOwnLibrary.jsf?faces-redirect=true";
	}
	
	public Map<String,String> getCountryParam(FacesContext fc){

		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		return params;

	}
	
	
	
	
	public String getLastUI() {
		return lastUI;
	}
	public void setLastUI(String lastUI) {
		this.lastUI = lastUI;
	}
	public String getShowShelfs() {
		return showShelfs;
	}
	public void setShowShelfs(String showShelfs) {
		this.showShelfs = showShelfs;
	}
	public String getShowBooks() {
		return showBooks;
	}
	public void setShowBooks(String showBooks) {
		this.showBooks = showBooks;
	}
	
	

}