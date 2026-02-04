package io.vertigo.shiny.models.composer;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.dataviz.chart.ShinyChartSerie;
import io.vertigo.shiny.models.dataviz.geomap.ShinyGeoPoint;

public final class ShinyComposer {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public ShinyBlock compose(final String jsonInput) {
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

			final List<ShinyGeoPoint> geoPoints = new ArrayList<>();
			if (rootNode.has("geoPoints")) {
				for (final JsonNode serieNode : rootNode.get("geoPoints")) {
					final double latitude = serieNode.get("latitude").asDouble();
					final double longitude = serieNode.get("longitude").asDouble();
					final String label = serieNode.get("label").asText();
					geoPoints.add(new ShinyGeoPoint(latitude, longitude, label));
				}
			}

			return switch (template.toLowerCase()) {
				case "bar" -> Shiny.barChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case "area" -> Shiny.areaChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case "line" -> Shiny.lineChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case "pie" -> Shiny.pieChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case "donut" -> Shiny.donutChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				case "radar" -> Shiny.radarChart().withTitle(title).withLabels(labels).addAllSeries(series).build();
				//---
				case "map" -> Shiny.geoMap().withTitle(title).addAllGeoPoints(geoPoints).build();
				default -> throw new VSystemException("Unknown template: " + template);
			};
		} catch (final Exception e) {
			throw new VSystemException(e, "Error composing ShinyModel from JSON");
		}
	}
}
