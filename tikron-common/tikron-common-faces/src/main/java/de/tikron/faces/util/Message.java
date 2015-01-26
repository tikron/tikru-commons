/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikron.faces.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Stellt verschiedene Methoden zum Abrufen und Senden von Nachrichten zur Verfügung.
 * 
 * @author Titus Kruse
 */
public class Message {

	/**
	 * Holt einen Nachrichtentext aus dem Message-Bundle der Anwendung.
	 * 
	 * @param msgId Nachrichten-Id.
	 * 
	 * @return Der Nachrichtentext.
	 */
	public static String getMessage(String msgId) {
		return getMessage(msgId, null);
	}

	/**
	 * Holt einen Nachrichtentext aus dem Message-Bundle der Anwendung und fügt übergebene Nachrichtendaten ein.
	 * 
	 * @param msgId Nachrichten-Id.
	 * @param msgData Nachrichtendaten oder null, falls keine Daten eingefügt werden sollen.
	 * 
	 * @return Der Nachrichtentext.
	 */
	public static String getMessage(String msgId, Object[] msgData) {
		return getMessage(null, msgId, msgData);
	}

	/**
	 * Holt einen Nachrichtentext aus dem Message-Bundle der Anwendung und fügt gegebenenfalls übergebene Nachrichtendaten
	 * ein.
	 * 
	 * @param bundleName Name der Nachrichtendatei oder null, falls die Nachrichtendatei der Anwendung verwendet werden
	 *          soll.
	 * @param msgId Nachrichten-Id.
	 * @param msgData Nachrichtendaten oder null, falls keine Daten eingefügt werden sollen.
	 * 
	 * @return Der Nachrichtentext.
	 */
	public static String getMessage(String bundleName, String msgId, Object[] msgData) {
		String text = null;
		Application app = FacesContext.getCurrentInstance().getApplication();
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		if (bundleName == null)
			bundleName = app.getMessageBundle();
		if (bundleName == null)
			bundleName = "de.tikron.manager.messages";
		ResourceBundle rb = ResourceBundle.getBundle(bundleName, locale);
		try {
			text = rb.getString(msgId);
		} catch (MissingResourceException e) {
			text = "*** Message with ID " + msgId + " not found.";
		}
		if (msgData != null) {
			MessageFormat mf = new MessageFormat(text, locale);
			text = mf.format(msgData, new StringBuffer(), null).toString();
		}
		return text;
	}

	/**
	 * Nachricht an den Faces-Context senden.
	 * 
	 * @param bundleName Name der Nachrichtendatei oder null, falls die Nachrichtendatei der Anwendung verwendet werden
	 *          soll.
	 * @param msgSummary Property-Name der Nachricht im Resource-Bundle.
	 * @param msgDetail Property-Name der Nachrichtendetails.
	 * @param severity Nachrichtenart.
	 * @param msgData Nachrichtendaten oder null, falls keine Daten eingefügt werden sollen.
	 */
	public static void sendMessage(String bundleName, String msgSummary, String msgDetail,
			FacesMessage.Severity severity, Object[] msgData) {
		FacesContext.getCurrentInstance().addMessage(null,
				getFacesMessage(bundleName, msgSummary, msgDetail, severity, msgData));
	}

	/**
	 * Nachricht an den Faces-Context senden.
	 * 
	 * @param bundleName Name der Nachrichtendatei oder null, falls die Nachrichtendatei der Anwendung verwendet werden
	 *          soll.
	 * @param msgSummary Property-Name der Nachricht im Resource-Bundle.
	 * @param msgDetail Property-Name der Nachrichtendetails.
	 * @param msgData Nachrichtendaten oder null, falls keine Daten eingefügt werden sollen.
	 */
	public static void sendMessage(String bundleName, String msgSummary, String msgDetail, Object[] msgData) {
		FacesContext.getCurrentInstance().addMessage(null, getFacesMessage(bundleName, msgSummary, msgDetail, msgData));
	}

	/**
	 * Nachricht an den Faces-Context senden.
	 * 
	 * @param bundleName Name der Nachrichtendatei oder null, falls die Nachrichtendatei der Anwendung verwendet werden
	 *          soll.
	 * @param msgSummary Property-Name der Nachricht im Resource-Bundle.
	 * @param msgData Nachrichtendaten oder null, falls keine Daten eingefügt werden sollen.
	 */
	public static void sendMessage(String bundleName, String msgSummary, Object[] msgData) {
		FacesContext.getCurrentInstance().addMessage(null, getFacesMessage(bundleName, msgSummary, msgData));
	}

	/**
	 * Faces-Message holen.
	 * 
	 * @param bundleName Name der Nachrichtendatei oder null, falls die Nachrichtendatei der Anwendung verwendet werden
	 *          soll.
	 * @param msgSummary Property-Name der Nachricht im Resource-Bundle.
	 * @param msgDetail Property-Name der Nachrichtendetails.
	 * @param severity Nachrichtenart.
	 * @param msgData Nachrichtendaten oder null, falls keine Daten eingefügt werden sollen.
	 * 
	 * @return Die Faces-Message.
	 */
	public static FacesMessage getFacesMessage(String bundleName, String msgSummary, String msgDetail,
			FacesMessage.Severity severity, Object[] msgData) {
		String messageText = Message.getMessage(bundleName, msgSummary, msgData);
		String messageDetailText = Message.getMessage(bundleName, msgDetail, msgData);
		return new FacesMessage(severity, messageText, messageDetailText);
	}

	/**
	 * Faces-Message holen.
	 * 
	 * @param bundleName Name der Nachrichtendatei oder null, falls die Nachrichtendatei der Anwendung verwendet werden
	 *          soll.
	 * @param msgSummary Property-Name der Nachricht im Resource-Bundle.
	 * @param msgDetail Property-Name der Nachrichtendetails.
	 * @param msgData Nachrichtendaten oder null, falls keine Daten eingefügt werden sollen.
	 * 
	 * @return Die Faces-Message.
	 */
	public static FacesMessage getFacesMessage(String bundleName, String msgSummary, String msgDetail, Object[] msgData) {
		String messageText = Message.getMessage(bundleName, msgSummary, msgData);
		String messageDetailText = Message.getMessage(bundleName, msgDetail, msgData);
		return new FacesMessage(messageText, messageDetailText);
	}

	/**
	 * Faces-Message holen.
	 * 
	 * @param bundleName Name der Nachrichtendatei oder null, falls die Nachrichtendatei der Anwendung verwendet werden
	 *          soll.
	 * @param msgSummary Property-Name der Nachricht im Resource-Bundle.
	 * @param msgData Nachrichtendaten oder null, falls keine Daten eingefügt werden sollen.
	 * 
	 * @return Die Faces-Message.
	 */
	public static FacesMessage getFacesMessage(String bundleName, String msgSummary, Object[] msgData) {
		String messageText = Message.getMessage(bundleName, msgSummary, msgData);
		return new FacesMessage(messageText);
	}

}
