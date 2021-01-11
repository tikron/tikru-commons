/**
 * Copyright (c) 2010 by Titus Kruse.
 */
package de.tikru.commons.faces.component;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 * Renderer für die Komponente OutputLabel. Enthält das Attribut "required" den Wert "true", wird dem Text des Labels
 * ein Stern (*) angehängt. Dies soll den Benutzer anzeigen, dass eine Eingabe im zugehörigen Eingabefeld erforderlich
 * ist.
 *
 * @since 13.12.2010
 * @author Titus Kruse
 */
@FacesRenderer(componentFamily = "javax.faces.Output", rendererType = "de.tikru.commons.faces.OutputLabel")
public class OutputLabelRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		super.encodeBegin(context, component);

		OutputLabel outputLabel = (OutputLabel) component;

		ResponseWriter writer = context.getResponseWriter();
		// Attribute der JSF-Komponente holen
		String clientId = outputLabel.getClientId(context);
		String forr = outputLabel.getFor();
		boolean required = outputLabel.getRequired();
		String requiredClass = outputLabel.getRequiredClass();
		String styleClass = outputLabel.getStyleClass();
		String value = outputLabel.getValue();

		// HTML-Tag label schreiben
		writer.startElement("label", outputLabel);
		writer.writeAttribute("id", clientId, null);
		if (styleClass != null)
			writer.writeAttribute("class", styleClass, null);
		if (forr != null)
			writer.writeAttribute("for", forr, null);
		writer.write(value);

		// HTML-Tag span zum Anzeigen des Sterns (*) schreiben
		if (required) {
			writer.startElement("span", outputLabel);
			if (requiredClass != null)
				writer.writeAttribute("class", requiredClass, null);
			writer.write("*");
			writer.endElement("span");
		}
		writer.endElement("label");
		writer.flush();
	}

}
