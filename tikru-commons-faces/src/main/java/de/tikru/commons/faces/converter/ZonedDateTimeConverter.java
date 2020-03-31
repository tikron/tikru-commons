/**
 * Copyright (c) 2017 by Titus Kruse.
 */
package de.tikru.commons.faces.converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Faces Converter converting between ZonedDateTime and localized formatted string.
 * 
 * @see https://stackoverflow.com/questions/34883270/
 *
 * @date 29.06.2017
 * 
 * @author BalusC
 * @author Titus Kruse
 */
@FacesConverter("zonedDateTimeConverter")
public class ZonedDateTimeConverter extends AbstractDateTimeConverter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return "";
		}

		if (modelValue instanceof ZonedDateTime) {
			return getDateTimeFormatter(context, component).format((ZonedDateTime) modelValue);
		} else {
			throw new ConverterException(new FacesMessage(modelValue + " is not a valid ZonedDateTime"));
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
			return null;
		}

		try {
			return ZonedDateTime.parse(submittedValue, getDateTimeFormatter(context, component));
		} catch (DateTimeParseException e) {
			throw new ConverterException(new FacesMessage(submittedValue + " is not a valid zoned date time"), e);
		}
	}
}