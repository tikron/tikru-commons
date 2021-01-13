/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Rejects unwanted Top Level Domains in an URL.
 * 
 * @author Titus Kruse
 * @since 14.11.2015
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedDomainValidator.class)
public @interface AllowedDomain {

	String message() default "URL does not match allowed domains.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	/**
	 * Array of valid domains names. The validation fails, if the URL ends with none of these domain names.
	 * 
	 * @return The domains (.de or .free.com)
	 */
	String[] value() default {};
}
