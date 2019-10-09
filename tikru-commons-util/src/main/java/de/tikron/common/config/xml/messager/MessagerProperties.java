package de.tikron.common.config.xml.messager;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.tikron.common.config.xml.BaseElement;

@XmlRootElement(name = "messager")
public class MessagerProperties extends BaseElement {
	
	@XmlElement(name = "mail")
	private MailMessagerProperties mailProperties;

	public MailMessagerProperties getMailProperties() {
		return mailProperties;
	}

	public void setMailProperties(MailMessagerProperties mailProperties) {
		this.mailProperties = mailProperties;
	}
}