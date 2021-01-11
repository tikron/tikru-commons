/**
 * Copyright (c) 2012 by Titus Kruse.
 */
package de.tikru.commons.faces.component;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 * Renderer für die Komponente OutputDelimited. Der Text im Attribut "value" wird ggf. auf die maximale Länge von
 * "maxLength" beschnitten.
 *
 * @since 04.02.0212
 * @author Titus Kruse
 */
@FacesRenderer(componentFamily = "javax.faces.Output", rendererType = "de.tikru.commons.faces.OutputDelimited")
public class OutputDelimitedRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		super.encodeBegin(context, component);

		OutputDelimited outputLabel = (OutputDelimited) component;

		ResponseWriter writer = context.getResponseWriter();
		// Attribute der JSF-Komponente holen
		String clientId = outputLabel.getClientId(context);
		int maxLength = outputLabel.getMaxLength();
		String styleClass = outputLabel.getStyleClass();
		String value = outputLabel.getValue();

		// Text konvertieren
		String outputText = value.length() > maxLength ? value.substring(0, maxLength) + "..." : value;
		// HTML-Tag span schreiben
		writer.startElement("span", component);
		writer.writeAttribute("id", clientId, null);
		if (styleClass != null)
			writer.writeAttribute("class", styleClass, null);
		writer.write(outputText);
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("span");
	}

}
