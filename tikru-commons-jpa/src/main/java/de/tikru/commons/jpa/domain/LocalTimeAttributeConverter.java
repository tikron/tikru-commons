package de.tikru.commons.jpa.domain;

import java.sql.Time;
import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA attribute converter converting between {@link ava.sql.Time} and {@link java.time.LocalTime}. 
 *
 * @date 29.06.2017
 * @author Titus Kruse
 */
@Converter(autoApply = true)
public class LocalTimeAttributeConverter implements AttributeConverter<LocalTime, Time> {

	@Override
	public Time convertToDatabaseColumn(LocalTime localTime) {
		return (localTime == null ? null : Time.valueOf(localTime));
	}

	@Override
	public LocalTime convertToEntityAttribute(Time sqlTime) {
		return (sqlTime == null ? null : sqlTime.toLocalTime());
	}
}
