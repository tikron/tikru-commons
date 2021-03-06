package de.tikru.commons.jpa.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA attribute converter converting between {@link java.sql.Timestamp} and {@link java.time.LocalDateTime}. It is assumed that persistence layer time zone is system default. 
 *
 * @author Titus Kruse
 * @since 29.06.2017
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
		return (localDateTime == null ? null : Timestamp.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
		return (timestamp == null ? null : timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
	}
}
