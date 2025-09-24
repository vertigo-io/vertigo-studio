package io.vertigo.shiny.components.dataviz.chakra;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyChakraSparkLine(String title, int[] data) implements ShinyComponent {
}