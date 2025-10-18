package io.vertigo.shiny.models.media.pdf;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

/**
 * Represents a PDF component to be rendered in the UI.
 */
public record ShinyPdfComponent(
		UUID id,
        String title,
        String pdfPath,
        int initialPage,
        String height) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyPdfComponent";
	}


    public ShinyPdfComponent {
        Assertion.check()
                .isNotBlank(pdfPath, "PDF path cannot be blank");
        Assertion.check()
                .isTrue(initialPage > 0, "Initial page must be a positive number");
        Assertion.check()
                .isNotBlank(height, "Height cannot be blank");
    }
}
