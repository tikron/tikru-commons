package de.tikron.common.config.xml.messager;

import javax.xml.bind.annotation.XmlSeeAlso;

import de.tikron.common.config.xml.BaseElement;

@XmlSeeAlso({PasswordAuthentication.class, OAuth2Authentication.class})
public abstract class Authentication extends BaseElement {
}
