package de.tikron.common.config.xml.messenger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tikron.common.config.xml.BaseElement;

@XmlRootElement(name = "messenger")
public class MessengerProperties extends BaseElement {
	
	@XmlElement(name = "dialog")
	private String dialogProperties;
	
	@XmlElement(name = "mail")
	private MailMessengerProperties mailProperties;

	public String getDialogProperties() {
		return dialogProperties;
	}

	public void setDialogProperties(String dialogProperties) {
		this.dialogProperties = dialogProperties;
	}

	public MailMessengerProperties getMailProperties() {
		return mailProperties;
	}

	public void setMailProperties(MailMessengerProperties mailProperties) {
		this.mailProperties = mailProperties;
	}
}
