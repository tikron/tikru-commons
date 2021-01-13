package de.tikru.commons.jpa.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA attribute converter converting between {@link java.sql.Timestamp} and {@link java.time.Instant}. It is assumed that persistence layer time zone is system default and instant time zone is UTC. 
 *
 * @author Titus Kruse
 * @since 29.06.2017
 */
@Converter(autoApply = true)
public class InstantAttributeConverter implements AttributeConverter<Instant, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(Instant instant) {
		return (instant == null ? null : Timestamp.from(instant.atZone(ZoneId.systemDefault()).toInstant()));
	}

	@Override
	public Instant convertToEntityAttribute(Timestamp timestamp) {
		return (timestamp == null ? null : timestamp.toInstant().atZone(ZoneId.systemDefault()).toInstant());
	}
}
