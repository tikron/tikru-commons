/**
 * Copyright (c) 2017 by Titus Kruse.
 */
package de.tikru.commons.faces.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Faces Converter converting between LocalDateTime and localized formatted string. It is assumed that business layer time zone is system default.
 * 
 * @see https://stackoverflow.com/questions/34883270/
 *
 * @date 29.06.2017
 * 
 * @author BalusC
 * @author Titus Kruse
 */
@FacesConverter("localDateTimeConverter")
public class LocalDateTimeConverter extends AbstractDateTimeConverter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return "";
		}

		if (modelValue instanceof LocalDateTime) {
			return getDateTimeFormatter(context, component).format(((LocalDateTime) modelValue).atZone(ZoneId.systemDefault()));
		} else {
			throw new ConverterException(new FacesMessage(modelValue + " is not a valid LocalDateTime"));
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
			return null;
		}

		try {
			return ZonedDateTime.parse(submittedValue, getDateTimeFormatter(context, component)).withZoneSameInstant(ZoneId.systemDefault())
					.toLocalDateTime();
		} catch (DateTimeParseException e) {
			throw new ConverterException(new FacesMessage(submittedValue + " is not a valid local date time"), e);
		}
	}
}