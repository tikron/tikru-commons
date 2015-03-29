/**
 * Copyright (c) 2015 by Titus Kruse.
 */
package de.tikron.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Compiles commands placed in a formatted text. Each command begins and ends with braces ([]) and includes a command
 * name followed by zero or more arguments. Command name and arguments within a command are separted by a spaces ( ).
 * Each command will be converted into ready HTML for the front end.
 * 
 * Example:
 * 
 * Lorem ipsum [a -e http://www.dolor.de "dolor"] sit amet, consectetuer [a /index.html "adipiscing elit"], sed diam 
 * [a nonummy] nibh euismod [b "tincidunt ut laoreet"] dolore magna aliquam erat volutpat.
 *
 * @date 23.02.2015
 * @author Titus Kruse
 */
public class FormattedTextCompiler {
	
	private static final FormattedTextCompiler INSTANCE = new FormattedTextCompiler(); 

	private static final Pattern COMMAND_PATTERN = Pattern.compile("\\[([^\\]]*)\\]");
	
	private static final String COMMAND_LINK = "a";
	
	private static final String COMMAND_BOLD = "b";
	
	/**
	 * Possible attributes controlling the compilation behaviour.
	 */
	public static enum Attribute {CONVERT_NEWLINE};
	
	private static final AttributeMap DEFAULT_ATTRIBUTES = new AttributeMap(){{
		put(Attribute.CONVERT_NEWLINE, Boolean.TRUE);
	}};
	
	private FormattedTextCompiler() {};
	
	public static FormattedTextCompiler getInstance() {
		return INSTANCE;
	}

	/**
	 * Compiles the given text with default attributes.
	 * 
	 * @param input The input text. Passing null results into a return value of null.
	 * @return The compiled text.
	 */
	public String compile(final CharSequence input) {
		return compile(input, DEFAULT_ATTRIBUTES);
	}

	/**
	 * Compiles the given text. Compilation behaviour can be controlled by one or more attributes.
	 * 
	 * @param input The input text. Passing null results into a return value of null.
	 * @param attributes The compilation attributes.
	 * @return The compiled text.
	 */
	public String compile(final CharSequence input, AttributeMap attributes) {
		if (input == null)
			return null;
		// Process commands
	  Matcher m = COMMAND_PATTERN.matcher(input);
	  StringBuffer sb = new StringBuffer(input.length());
	  while (m.find()) {
	    String commandLine = m.group(1);
	    Command cmd = parseCommandLine(commandLine);
	    if (COMMAND_LINK.equals(cmd.getCommandName())) {
				String html = processLink(cmd);
				m.appendReplacement(sb, Matcher.quoteReplacement(html));
	    } else if (COMMAND_BOLD.equals(cmd.getCommandName())) {
				String html = processBold(cmd);
				m.appendReplacement(sb, Matcher.quoteReplacement(html));
	    } else {
	    	System.err.println(MessageFormat.format("Unknown text compiler command: {0}.", cmd.getCommandName()));
	    }
	  }
	  m.appendTail(sb);
	  // Convert NewLine to <BR/>
	  String output = sb.toString();
	  if (attributes.getBoolean(Attribute.CONVERT_NEWLINE)) {
	  	output = convertNewline(output);
	  }
		return output;
	}
	
	private static String convertNewline(final String input) {
		if (input != null)
			return input.replace("\n", "<br />");
		else
			return input;
	}
	
	private static Command parseCommandLine(String commandLine) {
		Command command = null;
		boolean firstPart = true;
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(commandLine);
		while (m.find()) {
			String part = m.group(1).replace("\"", "");
			if (firstPart)
				command = new Command(part);
			else
				command.addArgument(part);
			firstPart = false;
		}
		return command;
	}
	
	private static class Command {
		private final String commandName;
		
		private final List<String> arguments = new ArrayList<String>();

		public Command(String commandName) {
			this.commandName = commandName;
		}
		
		public void addArgument(String arg){
			this.arguments.add(arg);
		}

		public String getCommandName() {
			return commandName;
		}
		
		public String[] getArguments() {
			return arguments.toArray(new String[0]);
		}
	}
	
	private static String processLink(Command cmd) {
		String title = "";
		String uri = "#";
		boolean external = false;
		String[] args = cmd.getArguments();
		int argsIdx = 0;
		if (args.length > argsIdx && args[argsIdx].equals("-e")) {
			external = true;
			argsIdx++;
		}
		if (args.length > argsIdx) {
			uri = args[argsIdx++];
		}
		if (args.length > argsIdx) {
			title = args[argsIdx++];
		}
		return buildLinkHtml(uri, title, external);
	}
	
	private static String buildLinkHtml(String uri, String title, boolean external) {
		StringBuffer sb = new StringBuffer();
		sb.append("<a href=\"");
		sb.append(uri);
		sb.append("\"");
		if (external) {
			sb.append(" rel=\"nofollow\"");
		} else {
			sb.append(" class=\"arrow\"");
		}
		sb.append(">");
		sb.append(title);
		if (external) {
			sb.append("<i class=\"fa fa-external-link\"></i>");
		}
		sb.append("</a>");
		return sb.toString();
	}
	
	private static String processBold(Command cmd) {
		String[] args = cmd.getArguments();
		StringBuffer sb = new StringBuffer();
		sb.append("<strong>");
		if (args.length >= 1) {
			sb.append(args[0]);
		}
		sb.append("</strong>");
		return sb.toString();
	}
	
	/**
	 * A Map of compiler controlling attributes.
	 *
	 * @date 23.02.2015
	 * @author Titus Kruse
	 */
	public static class AttributeMap extends HashMap<Attribute, Object> {
		
		/**
		 * Returns an attribute value of type Boolean.
		 * 
		 * @param key The key of the attribute to return.
		 * @return The attribute value. For easy handling as primitive type.
		 */
		public boolean getBoolean(Attribute key) {
			Object value = get(key);
			if (value instanceof Boolean) {
				return ((Boolean) value).booleanValue();
			} else {
				throw new IllegalArgumentException("Attribute value not of type Boolean: " + key);
			}
		}

	}

}