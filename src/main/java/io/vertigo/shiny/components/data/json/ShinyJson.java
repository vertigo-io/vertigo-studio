package io.vertigo.shiny.components.data.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyJson implements ShinyComponent {
	//	private final Shiny shiny;
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

	public ShinyJson() {
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

	public void render(ShinyWriter writer) {
		Assertion.check().isNotBlank(jsonString, "JSON string cannot be blank");
		//---
		try {
			final JsonNode rootNode = OBJECT_MAPPER.readTree(jsonString);
			printNode(writer, rootNode, "", true);
		} catch (final IOException e) {
			writer.println(ShinyColors.RED.fg("Error parsing JSON: " + e.getMessage()));
		}
	}

	private void printNode(final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		if (node.isObject()) {
			printObject(writer, node, indent, isLast);
		} else if (node.isArray()) {
			printArray(writer, node, indent, isLast);
		} else {
			printValue(writer, node, indent, isLast);
		}
	}

	private void printObject(final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		writer.println(bracesColor.fg("{"));
		final Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			final Entry<String, JsonNode> field = fields.next();
			final boolean lastField = !fields.hasNext();
			writer.print(indent + "  "
					+ labelColor.fg("\"" + field.getKey() + "\"")
					+ colonColor.fg(":") + " ");
			printNode(writer, field.getValue(), indent + "  ", lastField);
		}
		writer.print(indent + bracesColor.fg("}"));
		if (!isLast) {
			writer.print(commaColor.fg(","));
		}
		writer.println();
	}

	private void printArray(final ShinyWriter writer, JsonNode node, final String indent, final boolean isLast) {
		writer.println(bracketColor.fg("["));
		for (int i = 0; i < node.size(); i++) {
			final JsonNode element = node.get(i);
			final boolean lastElement = (i == node.size() - 1);
			writer.print(indent + "  ");
			printNode(writer, element, indent + "  ", lastElement);
		}
		writer.print(indent + bracketColor.fg("]"));
		if (!isLast) {
			writer.print(commaColor.fg(","));
		}
		writer.println();
	}

	private void printValue(final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
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
		writer.print(display);
		if (!isLast) {
			writer.print(commaColor.fg(","));
		}
		writer.println();
	}

}
