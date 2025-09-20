package io.vertigo.shiny.components.data.table;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

/**
 * Represents a table that can be printed in a terminal with colors,
 * borders and formatted numeric values.
 */
public record ShinyTable(
		String title,
		String noDataFound,
		String[] header,
		List<String[]> rows,
		@JsonIgnore ShinyTableStyle style) implements ShinyComponent {

	public ShinyTable {
		// Perform any final validations here before building the object
		Assertion.check()
				.isNotNull(rows)
				.when(rows.isEmpty(), () -> Assertion.check().isNotBlank(noDataFound, "noDataFound message is required when table is empty"))
				.isNotNull(header, "Table header is required");
	}

	// Static factory method to get a new Builder instance
	public static ShinyTableBuilder builder() {
		return new ShinyTableBuilder();
	}

	/**
	 * Prints the table to the console with the configured formatting.
	 */
	@Override
	public void render(final ShinyWriter writer) {
		new ShinyTableRenderer().render(this, writer);
	}

}
