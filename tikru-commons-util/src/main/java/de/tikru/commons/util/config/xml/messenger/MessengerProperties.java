package de.tikru.commons.util.config.xml.messenger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tikru.commons.util.config.xml.BaseElement;

@XmlRootElement(name = "messenger")
public class MessengerProperties extends BaseElement {
	
	@XmlElement(name = "mail")
	private MailMessengerProperties mailProperties;

	public MailMessengerProperties getMailProperties() {
		return mailProperties;
	}

	public void setMailProperties(MailMessengerProperties mailProperties) {
		this.mailProperties = mailProperties;
	}
}
