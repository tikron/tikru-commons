/**
	* Copyright (c) 2020 by Titus Kruse.
	*/

package de.tikru.commons.message.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
	* Base class of all JSON messenger configurations 
	*
	* @author Titus Kruse
	* @since Dec 15, 2020
	*/

@JsonTypeInfo(
	  use = JsonTypeInfo.Id.NAME, 
	  include = JsonTypeInfo.As.PROPERTY, 
	  property = "type")
	@JsonSubTypes({ 
	  @Type(value = MailMessengerConfiguration.class, name = "mail")
	})
public abstract class MessengerConfiguration {
}
