package io.vertigo.shiny.components.dataviz.chakra;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyChakraAreaChart(String title, String[] labels, int[] data) implements ShinyComponent {
}