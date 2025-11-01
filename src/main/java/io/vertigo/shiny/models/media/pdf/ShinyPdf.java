package io.vertigo.shiny.models.media.pdf;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

/**
 * Represents a PDF component to be rendered in the UI.
 */
public record ShinyPdf(
		String title,
		String pdfPath,
		int initialPage,
		String height) implements ShinyBlock {

	public ShinyPdf {
		Assertion.check()
				.isNotBlank(pdfPath, "PDF path cannot be blank")
				.isTrue(initialPage > 0, "Initial page must be a positive number")
				.isNotBlank(height, "Height cannot be blank");
	}

	@Override
	public String shinyType() {
		return "ShinyPdf";
	}
}
