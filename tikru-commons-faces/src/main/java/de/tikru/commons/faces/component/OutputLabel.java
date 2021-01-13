/**
 * Copyright (c) 2010 by Titus Kruse.
 */
package de.tikru.commons.faces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;

/**
 * Tag-Handler für das Tag OutputLabel.
 * 
 * Syntax: outputLabel [for="for"] [id="id"] [required="true|false] [styleClass="css-class"] [value="value"]
 *
 * @author Titus Kruse
 * @since 13.12.2010
 */
@FacesComponent("de.tikru.commons.faces.OutputLabel")
public class OutputLabel extends UIOutput {

	public static final String COMPONENT_TYPE = "de.tikru.commons.faces.OutputLabel";

	enum PropertyKeys {
		forr, required, requiredClass, styleClass, value
	};

	public OutputLabel() {
		setRendererType("de.tikru.commons.faces.OutputLabel");
	}

	public String getFor() {
		return (String) getStateHelper().eval(PropertyKeys.forr);
	}

	public void setFor(String forr) {
		getStateHelper().put(PropertyKeys.forr, forr);
	}

	public boolean getRequired() {
		return (Boolean) getStateHelper().eval(PropertyKeys.required, false);
	}

	public void setRequired(boolean required) {
		getStateHelper().put(PropertyKeys.required, required);
	}

	public String getRequiredClass() {
		return (String) getStateHelper().eval(PropertyKeys.requiredClass);
	}

	public void setRequiredClass(String requiredClass) {
		getStateHelper().put(PropertyKeys.requiredClass, requiredClass);
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
