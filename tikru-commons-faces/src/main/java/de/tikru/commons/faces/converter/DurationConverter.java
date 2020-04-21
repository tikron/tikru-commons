/**
 * Copyright (c) 2017 by Titus Kruse.
 */
package de.tikru.commons.faces.converter;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Faces Converter converting between Duration and formatted string. The external representation of Duration will be a time like hh:mm:ss instead of PHhhMmmSss.
 * 
 * @date 01.08.2017
 * 
 * @author Titus Kruse
 */
@FacesConverter("durationConverter")
public class DurationConverter extends AbstractDateTimeConverter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		if (modelValue == null) {
			return "";
		}

		if (modelValue instanceof Duration) {
			LocalTime time = LocalTime.ofNanoOfDay(((Duration) modelValue).toNanos());
			return getTimeFormatter(context, component).format(time);
		} else {
			throw new ConverterException(new FacesMessage(modelValue + " is not a valid Duration"));
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
			return null;
		}

		try {
			LocalTime time = LocalTime.parse(submittedValue, getTimeFormatter(context, component));
			return Duration.ofNanos(time.toNanoOfDay());
		} catch (DateTimeParseException e) {
			throw new ConverterException(new FacesMessage(submittedValue + " is not a valid local date time"), e);
		}
	}
}