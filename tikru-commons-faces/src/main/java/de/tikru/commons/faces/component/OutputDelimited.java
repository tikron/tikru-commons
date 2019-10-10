/**
 * Copyright (c) 2012 by Titus Kruse.
 */
package de.tikru.commons.faces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;

/**
 * Tag-Handler für das Tag OutputDelimited.
 * 
 * Syntax: OutputDelimited[id="id"] [maxlength="length"] [styleClass="css-class"] [value="value"]
 *
 * @date 04.02.2012
 * @author Titus Kruse
 */
@FacesComponent("de.tikru.commons.faces.OutputDelimited")
public class OutputDelimited extends UIOutput {

	public static final String COMPONENT_TYPE = "de.tikru.commons.faces.OutputDelimited";

	enum PropertyKeys {
		maxLength, styleClass, value
	};

	public OutputDelimited() {
		setRendererType("de.tikru.commons.faces.OutputDelimited");
	}

	public int getMaxLength() {
		return (Integer) getStateHelper().eval(PropertyKeys.maxLength, false);
	}

	public void setMaxLength(int maxLength) {
		getStateHelper().put(PropertyKeys.maxLength, maxLength);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(PropertyKeys.styleClass);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(PropertyKeys.styleClass, styleClass);
	}

	public String getValue() {
		return (String) getStateHelper().eval(PropertyKeys.value);
	}

	public void setValue(String value) {
		getStateHelper().put(PropertyKeys.value, value);
	}

}
