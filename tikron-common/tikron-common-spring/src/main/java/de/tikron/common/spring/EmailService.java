/**
 * Copyright (c) 2009 by Titus Kruse.
 */
package de.tikron.common.spring;

/**
 * Stellt Funktionen zum Versenden von E-Mails bereit.
 *
 * @date 23.12.2009
 * @author Titus Kruse
 */
public interface EmailService {

	/**
	 * Sendet eine E-Mail. Die E-Mail wird vom Webmaster an den Standardempfänger gesendet.
	 * 
	 * @param subject Der "Betreff" der E-Mail.
	 * @param content Der Nachrichtentext.
	 * @return true, falls das Senden erfolgreich war. Andernfalls false.
	 */
	public boolean sendEmail(String subject, String content);

	/**
	 * Sendet eine E-Mail. Die E-Mail wird vom Webmaster an den übergeben Empfänger gesendet.
	 * 
	 * @param recipient Die E-Mail-Adresse des Empängers.
	 * @param subject Der "Betreff" der E-Mail.
	 * @param content Der Nachrichtentext.
	 * @return true, falls das Senden erfolgreich war. Andernfalls false.
	 */
	public boolean sendEmail(String recipient, String subject, String content);

	/**
	 * Sendet eine E-Mail. Die E-Mail wird vom übergebenen Absender an den übergeben Empfänger gesendet.
	 * 
	 * @param sender Die E-Mail-Adresse des Absenders.
	 * @param recipient Die E-Mail-Adresse des Empängers.
	 * @param subject Der "Betreff" der E-Mail.
	 * @param content Der Nachrichtentext.
	 * @return true, falls das Senden erfolgreich war. Andernfalls false.
	 */
	public boolean sendEmail(String sender, String recipient, String subject, String content);

	/**
	 * Sendet eine E-Mail. Die E-Mail wird vom übergebenen Absender an den übergeben Empfänger gesendet.
	 * 
	 * @param sender Die E-Mail-Adresse des Absenders oder null, falls der Standardabsender verwendet werden soll.
	 * @param senderName Der Name des Absenders, oder null, falls kein Name hinzugefügt werden soll.
	 * @param recipient Die E-Mail-Adresse des Empängers, oder null, falls der Standardempfänger verwendet werden soll.
	 * @param subject Der "Betreff" der E-Mail.
	 * @param content Der Nachrichtentext.
	 * @return true, falls das Senden erfolgreich war. Andernfalls false.
	 */
	public boolean sendEmail(String sender, String senderName, String recipient, String subject, String content);

	/**
	 * Liefert die E-Mail-Adresse des Standardabsenders.
	 * 
	 * @return Die E-Mail-Adresse des Standardabsenders.
	 */
	public String getSender();

	/**
	 * Liefert die E-Mail-Adresse des Standardempfängers.
	 * 
	 * @return Die E-Mail-Adresse des Standardempfängers.
	 */
	public String getRecipient();

}
