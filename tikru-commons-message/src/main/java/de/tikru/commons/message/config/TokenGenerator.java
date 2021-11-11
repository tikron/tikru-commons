package de.tikru.commons.message.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
	  use = JsonTypeInfo.Id.NAME, 
	  include = JsonTypeInfo.As.PROPERTY, 
	  property = "type")
	@JsonSubTypes({ 
	  @Type(value = GoogleTokenGenerator.class, name = "google")
	})
public abstract class TokenGenerator {
}
