/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikru.commons.faces.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;

/**
 * Tag-Handler für das Tag OutputTextarea.
 * 
 * Syntax: outputTextarea [id="id"] [styleClass="css-class"] [value="value"]
 * 
 * @author Titus Kruse
 */
@FacesComponent("de.tikru.commons.faces.OutputTextarea")
public class OutputTextarea extends UIOutput {

	public static final String COMPONENT_TYPE = "de.tikru.commons.faces.OutputTextarea";

	enum PropertyKeys {
		styleClass, value
	};

	public OutputTextarea() {
		setRendererType("de.tikru.commons.faces.Textarea");
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
