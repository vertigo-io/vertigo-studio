package io.vertigo.shiny.components.data.chakra;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyChakraList(String title, String[] items) implements ShinyComponent {
}