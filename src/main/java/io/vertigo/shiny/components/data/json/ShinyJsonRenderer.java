package io.vertigo.shiny.components.data.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyComponentRenderer; // New import
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyJsonRenderer implements ShinyComponentRenderer<ShinyJson> { // Implements interface

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public ShinyJsonRenderer() { // Public no-arg constructor
		//private constructor
	}

	@Override // Override annotation
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyJson;
	}

	@Override // Override annotation
	public void render(final ShinyJson shinyJson, final ShinyWriter writer) { // Not static
		Assertion.check().isNotNull(shinyJson);
		Assertion.check().isNotNull(writer);
		//---
		try {
			final JsonNode rootNode = OBJECT_MAPPER.readTree(shinyJson.json());
			printNode(shinyJson, writer, rootNode, "", true);
		} catch (final IOException e) {
			writer.println(ShinyColors.RED.fg("Error parsing JSON: " + e.getMessage()));
		}
	}

	private static void printNode(final ShinyJson shinyJson, final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		if (node.isObject()) {
			printObject(shinyJson, writer, node, indent, isLast);
		} else if (node.isArray()) {
			printArray(shinyJson, writer, node, indent, isLast);
		} else {
			printValue(shinyJson, writer, node, indent, isLast);
		}
	}

	private static void printObject(final ShinyJson shinyJson, final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		writer.println(shinyJson.style().bracesColor().fg("{"));
		final Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			final Entry<String, JsonNode> field = fields.next();
			final boolean lastField = !fields.hasNext();
			writer.print(indent + "  "
					+ shinyJson.style().labelColor().fg("\"" + field.getKey() + "\"")
					+ shinyJson.style().colonColor().fg(":") + " ");
			printNode(shinyJson, writer, field.getValue(), indent + "  ", lastField);
		}
		writer.print(indent + shinyJson.style().bracesColor().fg("}"));
		if (!isLast) {
			writer.print(shinyJson.style().commaColor().fg(","));
		}
		writer.println();
	}

	private static void printArray(final ShinyJson shinyJson, final ShinyWriter writer, JsonNode node, final String indent, final boolean isLast) {
		writer.println(shinyJson.style().bracketColor().fg("["));
		for (int i = 0; i < node.size(); i++) {
			final JsonNode element = node.get(i);
			final boolean lastElement = (i == node.size() - 1);
			writer.print(indent + "  ");
			printNode(shinyJson, writer, element, indent + "  ", lastElement);
		}

		writer.print(indent + shinyJson.style().bracketColor().fg("]"));
		if (!isLast) {
			writer.print(shinyJson.style().commaColor().fg(","));
		}
		writer.println();
	}

	private static void printValue(final ShinyJson shinyJson, final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		final String display;
		if (node.isTextual()) {
			display = shinyJson.style().stringColor().fg("\"" + node.asText() + "\"");
		} else if (node.isNumber()) {
			display = shinyJson.style().numberColor().fg(node.asText());
		} else if (node.isBoolean()) {
			display = shinyJson.style().booleanColor().fg(node.asText());
		} else if (node.isNull()) {
			display = shinyJson.style().nullColor().fg("null");
		} else {
			display = ShinyColors.RED.bg("UNKNOW");
		}
		writer.print(display);
		if (!isLast) {
			writer.print(shinyJson.style().commaColor().fg(","));
		}
		writer.println();
	}
}
