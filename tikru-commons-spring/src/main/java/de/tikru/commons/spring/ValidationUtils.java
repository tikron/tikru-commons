/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikru.commons.spring;

import org.springframework.util.Assert;
import org.springframework.validation.Errors;

/**
 * Erweitert die von Spring angebotene Klasse um weitere Validatoren.
 * 
 * @author Titus Kruse
 * @since 29.12.2009
 */
public class ValidationUtils extends org.springframework.validation.ValidationUtils {

	private static final String EMAIL_MASK = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?";

	private static final String URL_MASK = "(https?:\\/\\/)?([\\dA-Za-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?";

	/**
	 * Validiert ein Feld auf dessen Länge. Das Feld ist ungültig, falls es länger als maxLength ist. Für den Vergleich
	 * wird der toString() auf den Feldwert angewendet.
	 * 
	 * @param errors Das Fehlerobjekt.
	 * @param field Der Name des Feldes.
	 * @param maxLength Die maximale Länge.
	 * @param errorCode Der Property-Schlüssel der Fehlernachricht.
	 */
	public static void rejectIfTooLong(Errors errors, String field, int maxLength, String errorCode) {
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if (value != null && value.toString().length() > maxLength) {
			errors.rejectValue(field, errorCode, new Object[] { maxLength },
					"Field value too long. Must be less or equal {0} characters.");
		}
	}

	/**
	 * Validiert ein Feld vom Typ Integer auf gültigen Wertebereich. Das Feld ist ungültig, falls der Feldwert kleiner
	 * oder größer als die Vergleichwerte ist.
	 * 
	 * @param errors Das Fehlerobjekt.
	 * @param field Der Name des Feldes.
	 * @param min Der kleinste gültige Wert.
	 * @param max Der größte gültige Wert.
	 * @param errorCode Der Property-Schlüssel der Fehlernachricht.
	 */
	public static void rejectIfOutOfRange(Errors errors, String field, int min, int max, String errorCode) {
		Assert.notNull(errors, "Errors object must not be null");
		Object value = (Integer) errors.getFieldValue(field);
		Assert.isInstanceOf(Integer.class, value, "Errors object must be of type Integer");
		if (value != null && (((Integer) value).intValue() < min || ((Integer) value).intValue() > max)) {
			errors.rejectValue(field, errorCode, new Object[] { min, max },
					"Field value invalid. Must be between {0} and {1}.");
		}
	}

	/**
	 * Validiert ein Feld vom Typ String auf gültige E-Mail-Adresse. Das Feld ist ungültig, falls der Feldwert keine
	 * gültige E-Mail-Adresse ist.
	 * 
	 * @param errors Das Fehlerobjekt.
	 * @param field Der Name des Feldes.
	 * @param errorCode Der Property-Schlüssel der Fehlernachricht.
	 */
	public static void rejectIfInvalidEmail(Errors errors, String field, String errorCode) {
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		Assert.isInstanceOf(String.class, value, "Errors object must be of type String");
		if (value != null && !((String) value).matches(EMAIL_MASK)) {
			errors.rejectValue(field, errorCode, "Field value is not a valid e-mail-address.");
		}
	}

	/**
	 * Validiert ein Feld vom Typ String auf gültige URL. Das Feld ist ungültig, falls der Feldwert keine gültige URL ist.
	 * 
	 * @param errors Das Fehlerobjekt.
	 * @param field Der Name des Feldes.
	 * @param errorCode Der Property-Schlüssel der Fehlernachricht.
	 */
	public static void rejectIfInvalidURL(Errors errors, String field, String errorCode) {
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		Assert.isInstanceOf(String.class, value, "Errors object must be of type String");
		if (value != null && !((String) value).matches(URL_MASK)) {
			errors.rejectValue(field, errorCode, "Field value is not a valid URL.");
		}
	}

	/**
	 * Validiert ein Feld vom Typ String auf ungültige Zeichen. Das Feld ist ungültig, falls der Feldwert eine der
	 * übergebenen Zeichenfolgen enthält.
	 * 
	 * @param errors Das Fehlerobjekt.
	 * @param field Der Name des Feldes.
	 * @param errorCode Der Property-Schlüssel der Fehlernachricht.
	 * @param invalidStrings Array aus ungültigen Zeichenfolgen.
	 */
	public static void rejectIfContainsString(Errors errors, String field, String errorCode, String[] invalidStrings) {
		rejectIfContainsString(errors, field, errorCode, "Field value contains invalid text.", invalidStrings);
	}

	private static void rejectIfContainsString(Errors errors, String field, String errorCode, String defaultMessage,
			String[] invalidStrings) {
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		Assert.isInstanceOf(String.class, value, "Errors object must be of type String");
		if (value != null) {
			for (String str : invalidStrings) {
				if (((String) value).toLowerCase().contains(str)) {
					errors.rejectValue(field, errorCode, defaultMessage);
					return;
				}
			}
		}
	}

}
