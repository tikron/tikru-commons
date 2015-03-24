/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.common;

import org.junit.Assert;
import org.junit.Test;

import de.tikron.common.FormattedTextCompiler.Attribute;
import de.tikron.common.FormattedTextCompiler.AttributeMap;

/**
 * Tests for {@link de.tikron.common.FormattedTextCompiler}.
 *
 * @date 24.03.2015
 * @author Titus Kruse
 */
public class FormattedTextCompilerTest {
	
	private static final String HTML_SAMPLE = "Lorem ipsum [a -e http://www.dolor.de \"dolor\"] sit amet, consectetuer [a /index.html \"adipiscing elit\"],\n sed diam [a nonummy] nibh euismod [b \"tincidunt ut laoreet\"] dolore magna aliquam erat volutpat.";
	
	private static final String HTML_RESULT = "Lorem ipsum <a href=\"http://www.dolor.de\" rel=\"nofollow\">dolor<i class=\"fa fa-external-link\"></i></a> sit amet, consectetuer <a href=\"/index.html\" class=\"arrow\">adipiscing elit</a>,<br /> sed diam <a href=\"nonummy\" class=\"arrow\"></a> nibh euismod <strong>tincidunt ut laoreet</strong> dolore magna aliquam erat volutpat.";
	
	private static final String NEWLINE_SAMPLE = "Lorem ipsum dolor sit amet,\n consectetuer adipiscing elit,\n sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.";
	
	private static final String NEWLINE_RESULT = "Lorem ipsum dolor sit amet,<br /> consectetuer adipiscing elit,<br /> sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.";
	
	@Test
	public void textShouldReturnHtmlText() {
		Assert.assertEquals("Unexpected HTML text: ", HTML_RESULT, FormattedTextCompiler.getInstance().compile(HTML_SAMPLE));
	}
	
	@Test
	public void textShouldReturnNewlineText() {
		Assert.assertEquals("Unexpected newline text: ", NEWLINE_RESULT, FormattedTextCompiler.getInstance().compile(NEWLINE_SAMPLE));
	}
	
	@Test
	public void textShouldReturnNonNewlineText() {
		AttributeMap attr = new AttributeMap();
		attr.put(Attribute.CONVERT_NEWLINE, Boolean.FALSE);
		Assert.assertEquals("Unexpected non-newline text: ", NEWLINE_SAMPLE, FormattedTextCompiler.getInstance().compile(NEWLINE_SAMPLE, attr));
	}

}
