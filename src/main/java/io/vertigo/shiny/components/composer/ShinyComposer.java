package io.vertigo.shiny.components.composer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.dataviz.ShinyChartSerie;

public final class ShinyComposer {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private enum Chart {
		pie,
		area,
		radar,
		bar,
		donut
	}

	public ShinyComponent compose(final String jsonInput) {
		System.out.println(">> before " + jsonInput);
		//clean
		int start = jsonInput.indexOf('{');
		int end = jsonInput.lastIndexOf('}');
		final String json = jsonInput.substring(start, end + 1);
		System.out.println(">> after " + jsonInput);

		try {
			final JsonNode rootNode = MAPPER.readTree(json);
			final String template = rootNode.get("template").asText();
			final String title = rootNode.get("title").asText();

			final List<String> labels = new ArrayList<>();
			if (rootNode.has("labels")) {
				for (final JsonNode labelNode : rootNode.get("labels")) {
					labels.add(labelNode.asText());
				}
			}

			final List<ShinyChartSerie> series = new ArrayList<>();
			if (rootNode.has("series")) {
				for (final JsonNode serieNode : rootNode.get("series")) {
					final String serieName = serieNode.get("name").asText();
					final List<Double> serieValues = new ArrayList<>();
					for (final JsonNode valueNode : serieNode.get("data")) {
						serieValues.add(valueNode.asDouble());
					}
					series.add(new ShinyChartSerie(serieName, serieValues));
				}
			}

			return switch (Chart.valueOf(template.toLowerCase())) {
				case bar -> Shiny.barChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case area -> Shiny.areaChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case pie -> Shiny.pieChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case donut -> Shiny.donutChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case radar -> Shiny.radarChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				default -> throw new VSystemException("Unknown template: " + template);
			};
		} catch (final Exception e) {
			throw new VSystemException(e, "Error composing ShinyComponent from JSON");
		}
	}
}
