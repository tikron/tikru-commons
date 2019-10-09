/**
 * Copyright (c) 2017 by Titus Kruse.
 */
package de.tikron.faces.converter;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Faces Converter converting between LocalTime and localized formatted string.
 * 
 * @see https://stackoverflow.com/questions/34883270/
 *
 * @date 29.07.2017
 * 
 * @author BalusC
 * @author Titus Kruse
 */
@FacesConverter("localTimeConverter")
public class LocalTimeConverter extends AbstractDateTimeConverter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return "";
		}

		if (modelValue instanceof LocalTime) {
			return getTimeFormatter(context, component).format(((LocalTime) modelValue));
		} else {
			throw new ConverterException(new FacesMessage(modelValue + " is not a valid LocalTime"));
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
			return null;
		}

		try {
			return LocalTime.parse(submittedValue, getTimeFormatter(context, component));
		} catch (DateTimeParseException e) {
			throw new ConverterException(new FacesMessage(submittedValue + " is not a valid local date time"), e);
		}
	}
}