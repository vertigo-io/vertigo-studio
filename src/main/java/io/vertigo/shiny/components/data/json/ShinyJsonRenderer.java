package io.vertigo.shiny.components.data.json;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.renderers.ShinyComponentRenderer;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyJsonRenderer implements ShinyComponentRenderer<ShinyJson, ShinyJsonStyle> {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyJson;
	}

	@Override
	public void render(final ShinyJson shinyJson, final ShinyJsonStyle style, final ShinyWriter writer) {
		Assertion.check()
				.isNotNull(shinyJson)
				.isNotNull(style)
				.isNotNull(writer);
		//---
		try {
			final JsonNode rootNode = OBJECT_MAPPER.readTree(shinyJson.json());
			printNode(shinyJson, style, writer, rootNode, "", true);
		} catch (final IOException e) {
			writer.println(ShinyColors.RED.fg("Error parsing JSON: " + e.getMessage()));
		}
	}

	private static void printNode(final ShinyJson shinyJson, final ShinyJsonStyle style, final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		if (node.isObject()) {
			printObject(shinyJson, style, writer, node, indent, isLast);
		} else if (node.isArray()) {
			printArray(shinyJson, style, writer, node, indent, isLast);
		} else {
			printValue(shinyJson, style, writer, node, indent, isLast);
		}
	}

	private static void printObject(final ShinyJson shinyJson, final ShinyJsonStyle style, final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
		//sonStyle = Shiny.theme().jsonStyle()

		writer.println(style.bracesColor().fg("{"));
		final Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			final Entry<String, JsonNode> field = fields.next();
			final boolean lastField = !fields.hasNext();
			writer.print(indent + "  "
					+ style.labelColor().fg("\"" + field.getKey() + "\"")
					+ style.colonColor().fg(":") + " ");
			printNode(shinyJson, style, writer, field.getValue(), indent + "  ", lastField);
		}
		writer.print(indent + style.bracesColor().fg("}"));
		if (!isLast) {
			writer.print(style.commaColor().fg(","));
		}
		writer.println();
	}

	private static void printArray(final ShinyJson shinyJson, final ShinyJsonStyle style, final ShinyWriter writer, JsonNode node, final String indent, final boolean isLast) {
		writer.println(style.bracketColor().fg("["));
		for (int i = 0; i < node.size(); i++) {
			final JsonNode element = node.get(i);
			final boolean lastElement = (i == node.size() - 1);
			writer.print(indent + "  ");
			printNode(shinyJson, style, writer, element, indent + "  ", lastElement);
		}

		writer.print(indent + style.bracketColor().fg("]"));
		if (!isLast) {
			writer.print(style.commaColor().fg(","));
		}
		writer.println();
	}

	private static void printValue(final ShinyJson shinyJson, final ShinyJsonStyle style, final ShinyWriter writer, final JsonNode node, final String indent, final boolean isLast) {
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
