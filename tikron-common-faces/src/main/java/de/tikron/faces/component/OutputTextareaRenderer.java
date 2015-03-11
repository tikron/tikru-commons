/**
 * Copyright (c) 2008 by Titus Kruse.
 */
package de.tikron.faces.component;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 * Renderer für die Komponente OutputTextarea. Zeilenvorschubzeichen werden durch entsprechende HTML-Tags ersetzt.
 * Dadurch werden die im Text gespeicherten Zeilenvorschübe korrekt dargestellt.
 * 
 * @author Titus Kruse
 */
@FacesRenderer(componentFamily = "javax.faces.Output", rendererType = "de.tikron.faces.OutputTextarea")
public class OutputTextareaRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		super.encodeBegin(context, component);

		OutputTextarea outputTextarea = (OutputTextarea) component;

		ResponseWriter writer = context.getResponseWriter();
		// Attribute der JSF-Komponente holen
		String id = outputTextarea.getClientId(context);
		String styleClass = outputTextarea.getStyleClass();
		String value = outputTextarea.getValue();
		// Text konvertieren
		String outputText = value != null ? value.replaceAll("\n", "<br>") : "";
		// HTML-Tag span schreiben
		writer.startElement("span", component);
		writer.writeAttribute("id", id, null);
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
