package io.vertigo.shiny.components.data.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyJson implements ShinyComponent {
	private String jsonString;
	private ShinyJsonStyle jsonStyle;

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public ShinyJson() {
		jsonStyle = Shiny.theme().jsonStyle();
	}

	public ShinyJson json(final String json) {
		this.jsonString = json;
		return this;
	}

	public ShinyJson style(final ShinyJsonStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.jsonStyle = style;
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
		writer.println(jsonStyle.bracesColor.fg("{"));
		final Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			final Entry<String, JsonNode> field = fields.next();
			final boolean lastField = !fields.hasNext();
			writer.print(indent + "  "
					+ jsonStyle.labelColor.fg("\"" + field.getKey() + "\"")
					+ jsonStyle.colonColor.fg(":") + " ");
			printNode(writer, field.getValue(), indent + "  ", lastField);
		}
		writer.print(indent + jsonStyle.bracesColor.fg("}"));
		if (!isLast) {
			writer.print(jsonStyle.commaColor.fg(","));
		}
		writer.println();
	}

	private void printArray(final ShinyWriter writer, JsonNode node, final String indent, final boolean isLast) {
		writer.println(jsonStyle.bracketColor.fg("["));
		for (int i = 0; i < node.size(); i++) {
			final JsonNode element = node.get(i);
			final boolean lastElement = (i == node.size() - 1);
			writer.print(indent + "  ");
			printNode(writer, element, indent + "  ", lastElement);
		}
		writer.print(indent + jsonStyle.bracketColor.fg("]"));
		if (!isLast) {
			writer.print(jsonStyle.commaColor.fg(","));
		}
		writer.println();
	}

	private void printValue(final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		final String display;
		if (node.isTextual()) {
			display = jsonStyle.stringColor.fg("\"" + node.asText() + "\"");
		} else if (node.isNumber()) {
			display = jsonStyle.numberColor.fg(node.asText());
		} else if (node.isBoolean()) {
			display = jsonStyle.booleanColor.fg(node.asText());
		} else if (node.isNull()) {
			display = jsonStyle.nullColor.fg("null");
		} else {
			display = ShinyColors.RED.bg("UNKNOW");
		}
		writer.print(display);
		if (!isLast) {
			writer.print(jsonStyle.commaColor.fg(","));
		}
		writer.println();
	}

}
