package io.vertigo.shiny.components.data.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyJson implements ShinyComponent {
	private final Shiny shiny;
	private String jsonString;

	private ShinyColor labelColor = ShinyColors.BLUE;

	private ShinyColor numberColor = ShinyColors.GREEN;
	private ShinyColor stringColor = ShinyColors.RED;
	private ShinyColor booleanColor = ShinyColors.BLACK_BRIGHT;
	private ShinyColor nullColor = ShinyColors.BLACK_BRIGHT;

	// : 
	private ShinyColor colonColor = ShinyColors.YELLOW;

	private ShinyColor commaColor = ShinyColors.WHITE;
	private ShinyColor bracketColor = ShinyColors.WHITE;
	private ShinyColor bracesColor = ShinyColors.WHITE;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public ShinyJson(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyJson json(final String json) {
		this.jsonString = json;
		return this;
	}

	public ShinyJson labelColor(final ShinyColor color) {
		this.labelColor = color;
		return this;
	}

	public ShinyJson numberColor(final ShinyColor color) {
		this.numberColor = color;
		return this;
	}

	public ShinyJson stringColor(final ShinyColor color) {
		this.stringColor = color;
		return this;
	}

	public ShinyJson colonColor(final ShinyColor color) {
		this.colonColor = color;
		return this;
	}

	public ShinyJson bracesColor(final ShinyColor color) {
		this.bracesColor = color;
		return this;
	}

	public ShinyJson bracketColor(final ShinyColor color) {
		this.bracketColor = color;
		return this;
	}

	public ShinyJson commaColor(final ShinyColor color) {
		this.commaColor = color;
		return this;
	}

	public ShinyJson booleanColor(final ShinyColor color) {
		this.booleanColor = color;
		return this;
	}

	public ShinyJson nullColor(final ShinyColor color) {
		this.nullColor = color;
		return this;
	}

	public void print() {
		Assertion.check().isNotBlank(jsonString, "JSON string cannot be blank");
		//---
		try {
			final JsonNode rootNode = OBJECT_MAPPER.readTree(jsonString);
			printNode(rootNode, "", true);
		} catch (final IOException e) {
			shiny.getWriter().println(ShinyColors.RED + "Error parsing JSON: " + e.getMessage() + ShinyColors.RESET);
		}
	}

	private void printNode(final JsonNode node, final String indent, final boolean isLast) {
		if (node.isObject()) {
			printObject(node, indent, isLast);
		} else if (node.isArray()) {
			printArray(node, indent, isLast);
		} else {
			printValue(node, indent, isLast);
		}
	}

	private void printObject(final JsonNode node, final String indent, final boolean isLast) {
		shiny.getWriter().println(bracesColor.fg("{"));
		final Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			final Entry<String, JsonNode> field = fields.next();
			final boolean lastField = !fields.hasNext();
			shiny.getWriter().print(indent + "  "
					+ labelColor.fg("\"" + field.getKey() + "\"")
					+ colonColor.fg(":") + " ");
			printNode(field.getValue(), indent + "  ", lastField);
		}
		shiny.getWriter().print(indent + bracesColor.fg("}"));
		if (!isLast) {
			shiny.getWriter().print(commaColor.fg(","));
		}
		shiny.getWriter().println();
	}

	private void printArray(final JsonNode node, final String indent, final boolean isLast) {
		shiny.getWriter().println(bracketColor + "[" + ShinyColors.RESET);
		for (int i = 0; i < node.size(); i++) {
			final JsonNode element = node.get(i);
			final boolean lastElement = (i == node.size() - 1);
			shiny.getWriter().print(indent + "  ");
			printNode(element, indent + "  ", lastElement);
		}
		shiny.getWriter().print(indent + bracketColor + "]" + ShinyColors.RESET);
		if (!isLast) {
			shiny.getWriter().print(commaColor + "," + ShinyColors.RESET);
		}
		shiny.getWriter().println();
	}

	private void printValue(final JsonNode node, final String indent, final boolean isLast) {
		final String display;
		if (node.isTextual()) {
			display = stringColor.fg("\"" + node.asText() + "\"");
		} else if (node.isNumber()) {
			display = numberColor.fg(node.asText());
		} else if (node.isBoolean()) {
			display = booleanColor.fg(node.asText());
		} else if (node.isNull()) {
			display = nullColor.fg("null");
		} else {
			display = ShinyColors.RED.bg("UNKNOW");
		}
		shiny.getWriter().print(display);
		if (!isLast) {
			shiny.getWriter().print(commaColor.fg(","));
		}
		shiny.getWriter().println();
	}

}
