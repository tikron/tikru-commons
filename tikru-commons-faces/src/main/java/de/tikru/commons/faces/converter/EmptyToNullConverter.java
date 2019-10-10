/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikru.commons.faces.converter;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Request parameters have the unwanted behaviour that they are still been sent even if they does not contain any value.
 * In the Java side they will be translated as an empty String. Besides, JSF has the unwanted behaviour that it sets a
 * non-required String property with the empty String value anyway instead of setting it with null. This converter will
 * convert any empty String value to null so that the String property will be set to null in case of an empty String.
 * 
 * Note: this converter doesn't work at all in Tomcat 6.0.16 or newer as its EL implementation coerces null back to
 * empty String again during the update model values phase (which is unfortunately correct as per the EL specification).
 * Vote for this issue if you want. To fix this you need to startup Tomcat with the following VM argument:
 * 
 * -Dorg.apache.el.parser.COERCE_TO_ZERO=false
 * 
 * @author Titus Kruse
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-use-in-jsf.html
 */
@FacesConverter(forClass = String.class)
public class EmptyToNullConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value == null || value.trim().length() == 0) {
			if (component instanceof EditableValueHolder) {
				((EditableValueHolder) component).setSubmittedValue(null);
			}
			return null;
		}
		return value;
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		return (value == null) ? "" : value.toString();
	}

}
