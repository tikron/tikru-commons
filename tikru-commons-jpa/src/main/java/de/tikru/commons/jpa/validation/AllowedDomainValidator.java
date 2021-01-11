/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.jpa.validation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Rejects unwanted Top Level Domains in an URL.
 *
 * @author Titus Kruse
 * @since 14.11.2015
 */
public class AllowedDomainValidator implements ConstraintValidator<AllowedDomain, String> {
	
	private Set<String> domains;

	@Override
	public void initialize(AllowedDomain annotation) {
		domains = new HashSet<String>(annotation.value().length);
		for (String value : annotation.value()) {
			domains.add(value);
		}
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext ctx) {
		if (value != null) {
			try {
				URL url = new URL(value);
				String host = url.getHost();
				// Valid, if host name ends with one of the given domains
				for (String domain : domains) {
					if (host.endsWith(domain)) {
						return true;
					}
				}
				return false;
			} catch (MalformedURLException e) {
				return false;
			}
		}
		return true;
	}

}
