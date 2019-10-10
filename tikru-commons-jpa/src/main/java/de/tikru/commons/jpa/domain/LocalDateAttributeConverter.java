package de.tikru.commons.jpa.domain;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA attribute converter converting between {@link java.sql.Date} and {@link java.time.LocalDate}. 
 *
 * @date 29.06.2017
 * @author Titus Kruse
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate localDate) {
//		return (localDate == null ? null : new Date(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
		return (localDate == null ? null : Date.valueOf(localDate));
	}

	@Override
	public LocalDate convertToEntityAttribute(Date sqlDate) {
		return (sqlDate == null ? null : sqlDate.toLocalDate());
	}
}