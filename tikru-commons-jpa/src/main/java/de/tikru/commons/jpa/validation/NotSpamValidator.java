/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Rejects unwanted text.
 *
 * @author Titus Kruse
 * @since 05.01.2015
 */
public class NotSpamValidator implements ConstraintValidator<NotSpam, String> {
	
	private Set<String> spamPhrases;

	@Override
	public void initialize(NotSpam annotation) {
		spamPhrases = new HashSet<String>(annotation.value().length);
		for (String value : annotation.value()) {
			spamPhrases.add(value.toLowerCase());
		}
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext ctx) {
		if (value != null) {
			for (String spamPhrase : spamPhrases) {
				if (value.toLowerCase().contains(spamPhrase)) {
					return false;
				}
			}
		}
		return true;
	}

}
