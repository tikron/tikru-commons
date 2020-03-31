/**
 * Copyright (c) 2017 by Titus Kruse.
 */
package de.tikru.commons.faces.converter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Faces Converter converting between LocalDate and localized formatted string.
 * 
 * @see https://stackoverflow.com/questions/34883270/
 *
 * @date 29.07.2017
 * 
 * @author BalusC
 * @author Titus Kruse
 */
@FacesConverter("localDateConverter")
public class LocalDateConverter extends AbstractDateTimeConverter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return "";
		}

		if (modelValue instanceof LocalDate) {
			return getDateFormatter(context, component).format(((LocalDate) modelValue));
		} else {
			throw new ConverterException(new FacesMessage(modelValue + " is not a valid LocalDate"));
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
			return null;
		}

		try {
			return LocalDate.parse(submittedValue, getDateFormatter(context, component));
		} catch (DateTimeParseException e) {
			throw new ConverterException(new FacesMessage(submittedValue + " is not a valid local date time"), e);
		}
	}
}