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
 * Rejects unwanted text.
 * 
 * Note: Can also be done by built in @Pattern annotation, but this is an experiment with a custom annotation.
 *
 * @author Titus Kruse
 * @since 05.01.2015
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSpamValidator.class)
public @interface NotSpam {

	String message() default "Value contains unwanted text (spam).";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
	/**
	 * Array of Spam phrases. The validation fails, if the field contains one of the given Spam phraes.
	 * 
	 * @return The Spam phrases.
	 */
	String[] value() default {};
}
