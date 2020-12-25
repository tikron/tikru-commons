package de.tikru.commons.util.message.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
	  use = JsonTypeInfo.Id.NAME, 
	  include = JsonTypeInfo.As.PROPERTY, 
	  property = "type")
	@JsonSubTypes({ 
	  @Type(value = PasswordAuthentication.class, name = "password"), 
	  @Type(value = OAuth2Authentication.class, name = "oauth2") 
	})
public abstract class Authentication {
}
