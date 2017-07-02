package de.tikron.jpa.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA attribute converter converting between {@link ava.sql.Timestamp} and {@link ava.time.Instant}. It is assumed that persistence layer time zone is system default and instant time zone is UTC. 
 *
 * @date 29.06.2017
 * @author Titus Kruse
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
