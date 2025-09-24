package io.vertigo.shiny.components.data.chakra;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyChakraTable(String id, String title, String noDataFound, String[] header, String[][] rows, boolean sortable, int sortColumn, String sortDirection) implements ShinyComponent {
}