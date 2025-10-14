package io.vertigo.shiny.components.media.pdf;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

/**
 * Represents a PDF component to be rendered in the UI.
 */
public record ShinyPdfComponent(
        String title,
        String pdfPath,
        int initialPage,
        String height) implements ShinyComponent {

	@Override
	public String type() {
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
