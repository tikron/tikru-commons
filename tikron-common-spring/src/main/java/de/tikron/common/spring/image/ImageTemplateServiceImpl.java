/**
 * Copyright (c) 2011 by Titus Kruse.
 */
package de.tikron.common.spring.image;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

/**
 * Default implementation of an image template service loading the template definitions from a XML file.
 *
 * @date 01.09.2011
 * @author Titus Kruse
 */
public class ImageTemplateServiceImpl implements ImageTemplateService {

	private static Logger LOGGER = Logger.getLogger(ImageTemplateServiceImpl.class);

	private Resource templateDefinition;

	private Map<String, ImageTemplate> templates;

	@PostConstruct
	@SuppressWarnings("unchecked")
	public void init() {
		templates = new HashMap<String, ImageTemplate>();

		LOGGER.info(MessageFormat.format("Loading XML image template defintion from resource {0}.", templateDefinition));

		InputStream inputStream = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			// Access resource
			inputStream = templateDefinition.getInputStream();
			// Parse definition XML file
			Document document = builder.build(inputStream);
			Element rootElement = document.getRootElement();
			List<Element> templateElements = rootElement.getChildren("template");
			for (Element templateElement : templateElements) {
				String name = null;
				int width = 0, height = 0, limit = 0;
				ImageSignature signature = null;
				// Name
				Element nameElement = templateElement.getChild("name");
				if (nameElement != null) {
					name = nameElement.getText();
				} else {
					LOGGER.warn("Missing required element \"name\" in template definition");
					continue;
				}
				// Dimensions
				Element dimensionElement = templateElement.getChild("dimension");
				if (dimensionElement != null) {
					Element widthElement = dimensionElement.getChild("width");
					if (widthElement != null) {
						width = Integer.parseInt(widthElement.getText());
					}
					Element heightElement = dimensionElement.getChild("height");
					if (heightElement != null) {
						height = Integer.parseInt(heightElement.getText());
					}
				}
				// Limit
				Element limitElement = templateElement.getChild("limit");
				if (limitElement != null) {
					limit = Integer.parseInt(limitElement.getText());
				}
				// Signature
				Element signatureElement = templateElement.getChild("signature");
				if (signatureElement != null) {
					String text = signatureElement.getChildText("text");
					signature = new ImageSignature(text);
					Element sizeElement = signatureElement.getChild("size");
					if (sizeElement != null) {
						signature.setSize(Integer.parseInt(sizeElement.getText()));
					}
					Element opacityElement = signatureElement.getChild("opacity");
					if (opacityElement != null) {
						signature.setOpacity(Float.parseFloat(opacityElement.getText()));
					}
					Element positionElement = signatureElement.getChild("position");
					if (positionElement != null) {
						signature.setMarginHorizontal(Integer.parseInt(positionElement.getChildText("horizontal")));
						signature.setMarginVertical(Integer.parseInt(positionElement.getChildText("vertical")));
					}
				}
				// Add template to map
				if (width != 0 && height != 0) {
					templates.put(name, new ImageTemplate(name, width, height, signature));
				} else if (limit != 0) {
					templates.put(name, new ImageTemplate(name, limit, signature));
				} else {
					LOGGER.warn(MessageFormat.format("Nor dimensions neither limit element specified for template {0}.", name));
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOGGER.error(MessageFormat.format("Exception occured:", e.getMessage()));
		} catch (JDOMException e) {
			e.printStackTrace();
			LOGGER.error(MessageFormat.format("Exception occured:", e.getMessage()));
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(MessageFormat.format("Exception occured:", e.getMessage()));
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tikron.common.spring.image.ImageTemplateService#getTemplate(java.lang.String)
	 */
	@Override
	public ImageProperties getTemplate(String name) {
		return templates.get(name);
	}

	@Required
	public void setTemplateDefinition(Resource templateDefinition) {
		this.templateDefinition = templateDefinition;
	}

}
