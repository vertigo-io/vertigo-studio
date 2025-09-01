package io.vertigo.shell.shiny.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyJson {
	private final Shiny shiny;
	private String jsonString;

	private String labelColor = ShinyColors.BLUE;
	private String numberColor = ShinyColors.GREEN;
	private String stringColor = ShinyColors.RED;
	private String separatorColor = ShinyColors.YELLOW;
	private String defaultColor = ShinyColors.WHITE; // For brackets, commas, boolean, null

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

	public ShinyJson labelColor(final String color) {
		this.labelColor = color;
		return this;
	}

	public ShinyJson numberColor(final String color) {
		this.numberColor = color;
		return this;
	}

	public ShinyJson stringColor(final String color) {
		this.stringColor = color;
		return this;
	}

	public ShinyJson separatorColor(final String color) {
		this.separatorColor = color;
		return this;
	}

	public ShinyJson defaultColor(final String color) {
		this.defaultColor = color;
		return this;
	}

	// New methods for specific color customization
	public ShinyJson bracesColor(final String color) {
		this.defaultColor = color; // Braces use defaultColor
		return this;
	}

	public ShinyJson bracketColor(final String color) {
		this.defaultColor = color; // Brackets use defaultColor
		return this;
	}

	public ShinyJson colonColor(final String color) {
		this.separatorColor = color; // Colon uses separatorColor
		return this;
	}

	public ShinyJson commaColor(final String color) {
		this.defaultColor = color; // Comma uses defaultColor
		return this;
	}

	public ShinyJson booleanColor(final String color) {
		this.defaultColor = color; // Boolean uses defaultColor
		return this;
	}

	public ShinyJson nullColor(final String color) {
		this.defaultColor = color; // Null uses defaultColor
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
		shiny.getWriter().println(defaultColor + "{" + ShinyColors.RESET);
		final Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			final Entry<String, JsonNode> field = fields.next();
			final boolean lastField = !fields.hasNext();
			shiny.getWriter().print(indent + "  " + labelColor + "\"" + field.getKey() + "\"" + ShinyColors.RESET + separatorColor + ":" + ShinyColors.RESET + " ");
			printNode(field.getValue(), indent + "  ", lastField);
		}
		shiny.getWriter().print(indent + defaultColor + "}" + ShinyColors.RESET);
		if (!isLast) {
			shiny.getWriter().print(defaultColor + "," + ShinyColors.RESET);
		}
		shiny.getWriter().println();
	}

	private void printArray(final JsonNode node, final String indent, final boolean isLast) {
		shiny.getWriter().println(defaultColor + "[" + ShinyColors.RESET);
		for (int i = 0; i < node.size(); i++) {
			final JsonNode element = node.get(i);
			final boolean lastElement = (i == node.size() - 1);
			shiny.getWriter().print(indent + "  ");
			printNode(element, indent + "  ", lastElement);
		}
		shiny.getWriter().print(indent + defaultColor + "]" + ShinyColors.RESET);
		if (!isLast) {
			shiny.getWriter().print(defaultColor + "," + ShinyColors.RESET);
		}
		shiny.getWriter().println();
	}

	private void printValue(final JsonNode node, final String indent, final boolean isLast) {
		if (node.isTextual()) {
			shiny.getWriter().print(stringColor + "\"" + node.asText() + "\"" + ShinyColors.RESET);
		} else if (node.isNumber()) {
			shiny.getWriter().print(numberColor + node.asText() + ShinyColors.RESET);
		} else if (node.isBoolean()) {
			shiny.getWriter().print(defaultColor + node.asText() + ShinyColors.RESET);
		} else if (node.isNull()) {
			shiny.getWriter().print(defaultColor + "null" + ShinyColors.RESET);
		}
		if (!isLast) {
			shiny.getWriter().print(defaultColor + "," + ShinyColors.RESET);
		}
		shiny.getWriter().println();
	}
}
