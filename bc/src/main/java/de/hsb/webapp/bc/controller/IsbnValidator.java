package de.hsb.webapp.bc.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

/**
 * This class validates the ISBN of the books.
 * 
 * @author Thomas Schrul, Michael Günster, Andre Schriever
 *
 */
@FacesValidator(value = "isbnValidator")
public class IsbnValidator implements javax.faces.validator.Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String isbn = String.valueOf(value);
		ArrayList<Integer> isbnintarray = new ArrayList<Integer>();
		FacesMessage message;
		for (int i = 0; i < isbn.length(); i++)
			isbnintarray.add(isbn.charAt(i) - 48); // ASCII -> Integer
		if (!isbn.matches("[0-9]+")) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN not valid",
					"Use digits only!");
			throw new ValidatorException(message);
		} else if (isbn.length() < 10 || isbn.length() > 13) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN not valid",
					"ISBN number to short or to long!");
			throw new ValidatorException(message);
		} else if (!isValid(isbnintarray)) {
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ISBN not valid", "ISBN is not correct/valid!");
			throw new ValidatorException(message);
		}
	}

	/**
	 * Checks if the ISBN is valid by calculating the check number.
	 * 
	 * @param digits
	 *            Digits of the ISBN
	 * @return True if ISBN is valid.
	 */
	private boolean isValid(ArrayList<Integer> digits) {
		int length = digits.size();
		// sum of digits
		int sum = 0;
		// result to compare with pz
		int res = 0;
		// pz = check number of ISBN
		int pz;
		// pz is last number of ISBN
		pz = digits.get(length - 1);
		// for ISBN 13
		if (length == 13) {
			for (int i = 1; i < length; ++i) {
				Integer digit = digits.get(length - i - 1);
				// every third number multiplied with 3
				if (i % 2 == 1)
					digit *= 3;
				sum += digit;
			}
			// calculating if check number is valid
			res = 10 - (sum % 10);
			if (res == 10) {
				return pz == 0;
			} else {
				return pz == res;
			}
		}
		// for ISBN 10
		else {
			for (int i = 1; i < length; ++i) {
				Integer digit = digits.get(i - 1);
				digit *= i;
				sum += digit;
			}
			res = sum % 11;
			if (res == 10) {
				return pz == 'X' - 48; // ASCII -> Integer
			} else {
				return pz == res;
			}
		}
	}
}
