package de.tikru.commons.util.config.xml.messager;

import javax.xml.bind.annotation.XmlSeeAlso;

import de.tikru.commons.util.config.xml.BaseElement;

@XmlSeeAlso({PasswordAuthentication.class, OAuth2Authentication.class})
public abstract class Authentication extends BaseElement {
}
