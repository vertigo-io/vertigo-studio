package io.vertigo.shiny.components.data.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public record ShinyJson(
		String json,
		ShinyJsonStyle style) implements ShinyComponent {

	public ShinyJson {
		Assertion.check().isNotBlank(json, "JSON string cannot be blank");
	}

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	// Static factory method to get a new Builder instance
	public static ShinyJsonBuilder builder() {
		return new ShinyJsonBuilder();
	}

	public void render(ShinyWriter writer) {
		Assertion.check().isNotBlank(json, "JSON string cannot be blank");
		//---
		try {
			final JsonNode rootNode = OBJECT_MAPPER.readTree(json);
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
		writer.println(style.bracesColor().fg("{"));
		final Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			final Entry<String, JsonNode> field = fields.next();
			final boolean lastField = !fields.hasNext();
			writer.print(indent + "  "
					+ style.labelColor().fg("\"" + field.getKey() + "\"")
					+ style.colonColor().fg(":") + " ");
			printNode(writer, field.getValue(), indent + "  ", lastField);
		}
		writer.print(indent + style.bracesColor().fg("}"));
		if (!isLast) {
			writer.print(style.commaColor().fg(","));
		}
		writer.println();
	}

	private void printArray(final ShinyWriter writer, JsonNode node, final String indent, final boolean isLast) {
		writer.println(style.bracketColor().fg("["));
		for (int i = 0; i < node.size(); i++) {
			final JsonNode element = node.get(i);
			final boolean lastElement = (i == node.size() - 1);
			writer.print(indent + "  ");
			printNode(writer, element, indent + "  ", lastElement);
		}

		writer.print(indent + style.bracketColor().fg("]"));
		if (!isLast) {
			writer.print(style.commaColor().fg(","));
		}
		writer.println();
	}

	private void printValue(final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		final String display;
		if (node.isTextual()) {
			display = style.stringColor().fg("\"" + node.asText() + "\"");
		} else if (node.isNumber()) {
			display = style.numberColor().fg(node.asText());
		} else if (node.isBoolean()) {
			display = style.booleanColor().fg(node.asText());
		} else if (node.isNull()) {
			display = style.nullColor().fg("null");
		} else {
			display = ShinyColors.RED.bg("UNKNOW");
		}
		writer.print(display);
		if (!isLast) {
			writer.print(style.commaColor().fg(","));
		}
		writer.println();
	}
}
