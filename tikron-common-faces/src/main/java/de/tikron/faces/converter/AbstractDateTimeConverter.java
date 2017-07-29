/**
 * Copyright (c) 2017 by Titus Kruse.
 */
package de.tikron.faces.converter;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Abstract Date Time Converter with common methods to retrieve component attributes. The format, style, locale and time zone for the presentation input/output can be defined by faces compnent attributes.
 *
 * @date 29.06.2017
 * 
 * @author BalusC
 * @author Titus Kruse
 */
public abstract class AbstractDateTimeConverter implements Converter {
	
	// Zone Offset of internal application times. Should by UTC, but currently no need to use it.
	protected static final ZoneOffset INTERNAL_ZONE_OFFSET = ZoneOffset.of("+02");

	/**
	 * Returns the faces component formatting pattern attribute value.
	 * 
	 * @param component
	 * @return The pattern or null, if not specified.
	 */
	protected String getPattern(UIComponent component) {
		return (String) component.getAttributes().get("pattern");
	}
	
	/**
	 * Returns the faces component date style attribute value.
	 * 
	 * @param component
	 * @return The FormatStyle or Style.MEDIUM, if not specified.
	 */
	protected FormatStyle getDateStyle(UIComponent component) {
		String style = (String) component.getAttributes().get("dateStyle");
		if (style != null) {
			return FormatStyle.valueOf(style.toUpperCase());
		}
		return FormatStyle.MEDIUM;
	}
	
	/**
	 * Returns the faces component time style attribute value.
	 * 
	 * @param component
	 * @return The FormatStyle or Style.MEDIUM, if not specified.
	 */
	protected FormatStyle getTimeStyle(UIComponent component) {
		String style = (String) component.getAttributes().get("timeStyle");
		if (style != null) {
			return FormatStyle.valueOf(style.toUpperCase());
		}
		return FormatStyle.MEDIUM;
	}

	/**
	 * Returns the faces component locale identifier attribute value.
	 * 
	 * @param component
	 * @return The Locale or the ViewRoot locale, if not specified.
	 */
	protected Locale getLocale(FacesContext context, UIComponent component) {
		Object locale = component.getAttributes().get("locale");
		return (locale instanceof Locale) ? (Locale) locale
				: (locale instanceof String) ? new Locale((String) locale) : context.getViewRoot().getLocale();
	}

	/**
	 * Returns the faces component time zone identifier attribute value.
	 * 
	 * @param component
	 * @return The ZoneId or Style.MEDIUM, if not specified.
	 */
	protected ZoneId getZoneId(UIComponent component) {
		Object timeZone = component.getAttributes().get("timeZone");
		return (timeZone instanceof TimeZone) ? ((TimeZone) timeZone).toZoneId()
				: (timeZone instanceof String) ? ZoneId.of((String) timeZone) : ZoneId.of("Europe/Berlin");
	}

	/**
	 * Returns the DateTimeFormatter based on the given attributes to format/parse a date and time combination.
	 * 
	 * @param context
	 * @param component
	 * @return The DateTimeFormatter.
	 */
	protected DateTimeFormatter getDateTimeFormatter(FacesContext context, UIComponent component) {
		String pattern = getPattern(component);
		ZoneId zoneId = getZoneId(component);
		if (pattern != null) {
			return DateTimeFormatter.ofPattern(pattern, getLocale(context, component));
		} else if (zoneId != null){
			return DateTimeFormatter.ofLocalizedDateTime(getDateStyle(component), getTimeStyle(component)).withLocale(getLocale(context, component)).withZone(zoneId);
		} else {
			return DateTimeFormatter.ofLocalizedDateTime(getDateStyle(component), getTimeStyle(component)).withLocale(getLocale(context, component));
		}
	}

	/**
	 * Returns the DateTimeFormatter based on the given attributes to format/parse a date.
	 * 
	 * @param context
	 * @param component
	 * @return The DateTimeFormatter.
	 */
	protected DateTimeFormatter getDateFormatter(FacesContext context, UIComponent component) {
		String pattern = getPattern(component);
		ZoneId zoneId = getZoneId(component);
		if (pattern != null) {
			return DateTimeFormatter.ofPattern(pattern, getLocale(context, component));
		} else if (zoneId != null){
			return DateTimeFormatter.ofLocalizedDate(getDateStyle(component)).withLocale(getLocale(context, component)).withZone(zoneId);
		} else {
			return DateTimeFormatter.ofLocalizedDate(getDateStyle(component)).withLocale(getLocale(context, component));
		}
	}

	/**
	 * Returns the DateTimeFormatter based on the given attributes to format/parse a time.
	 * 
	 * @param context
	 * @param component
	 * @return The DateTimeFormatter.
	 */
	protected DateTimeFormatter getTimeFormatter(FacesContext context, UIComponent component) {
		String pattern = getPattern(component);
		ZoneId zoneId = getZoneId(component);
		if (pattern != null) {
			return DateTimeFormatter.ofPattern(pattern, getLocale(context, component));
		} else if (zoneId != null){
			return DateTimeFormatter.ofLocalizedTime(getTimeStyle(component)).withLocale(getLocale(context, component)).withZone(zoneId);
		} else {
			return DateTimeFormatter.ofLocalizedTime(getTimeStyle(component)).withLocale(getLocale(context, component));
		}
	}

}
