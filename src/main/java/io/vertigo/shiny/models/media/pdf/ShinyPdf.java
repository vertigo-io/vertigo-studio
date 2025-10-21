package io.vertigo.shiny.models.media.pdf;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

/**
 * Represents a PDF component to be rendered in the UI.
 */
public record ShinyPdf(
		UUID id,
		String title,
		String pdfPath,
		int initialPage,
		String height) implements ShinyModel {

	public ShinyPdf {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotBlank(pdfPath, "PDF path cannot be blank");
		Assertion.check()
				.isTrue(initialPage > 0, "Initial page must be a positive number");
		Assertion.check()
				.isNotBlank(height, "Height cannot be blank");
	}

	@Override
	public String shinyType() {
		return "ShinyPdf";
	}
}
