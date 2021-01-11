/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikru.commons.util;

import org.junit.Assert;
import org.junit.Test;

import de.tikru.commons.util.FormattedTextCompiler.AttrCommandAction;
import de.tikru.commons.util.FormattedTextCompiler.AttrNewLineAction;
import de.tikru.commons.util.FormattedTextCompiler.Attribute;
import de.tikru.commons.util.FormattedTextCompiler.AttributeMap;

/**
 * Tests for {@link de.tikru.commons.util.FormattedTextCompiler}.
 *
 * @author Titus Kruse
 * @since 24.03.2015
 */
public class FormattedTextCompilerTest {
	
	private static final String HTML_SAMPLE = "Lorem ipsum [a -e http://www.dolor.de \"dolor\"] sit amet, consectetuer [a /index.html \"adipiscing elit\"],\n sed diam [a nonummy] nibh euismod [b \"tincidunt ut laoreet\"] dolore magna aliquam erat volutpat.";
	
	private static final String HTML_RESULT = "Lorem ipsum <a href=\"http://www.dolor.de\" rel=\"nofollow\">dolor<i class=\"fa fa-external-link\"></i></a> sit amet, consectetuer <a href=\"/index.html\" class=\"arrow\">adipiscing elit</a>,<br /> sed diam <a href=\"#\" class=\"arrow\">nonummy</a> nibh euismod <strong>tincidunt ut laoreet</strong> dolore magna aliquam erat volutpat.";
	
	private static final String COMMANDS_DISCARDED = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit,\n sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.";
	
	private static final String NEWLINE_SAMPLE = "Lorem ipsum dolor sit amet,\n consectetuer adipiscing elit,\n sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.";
	
	private static final String NEWLINE_RESULT = "Lorem ipsum dolor sit amet,<br /> consectetuer adipiscing elit,<br /> sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.";
	
	private static final String NEWLINE_DISCARDED = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.";
	
	@Test
	public void textShouldReturnHtmlText() {
		Assert.assertEquals("Unexpected HTML text: ", HTML_RESULT, FormattedTextCompiler.getInstance().compile(HTML_SAMPLE));
	}
	
	@Test
	public void textShouldDiscardHtmlText() {
		AttributeMap attr = new AttributeMap();
		attr.put(Attribute.PROCESS_COMMANDS, AttrCommandAction.DISCARD);
		Assert.assertEquals("Unexpected discarded text: ", COMMANDS_DISCARDED, FormattedTextCompiler.getInstance().compile(HTML_SAMPLE, attr));
	}
	
	@Test
	public void textShouldReturnNewlineText() {
		Assert.assertEquals("Unexpected newline text: ", NEWLINE_RESULT, FormattedTextCompiler.getInstance().compile(NEWLINE_SAMPLE));
	}
	
	@Test
	public void textShouldReturnDiscardNewlineText() {
		AttributeMap attr = new AttributeMap();
		attr.put(Attribute.CONVERT_NEWLINE, AttrNewLineAction.DISCARD);
		Assert.assertEquals("Unexpected non-newline text: ", NEWLINE_DISCARDED, FormattedTextCompiler.getInstance().compile(NEWLINE_SAMPLE, attr));
	}

}
