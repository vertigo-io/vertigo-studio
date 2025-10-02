package io.vertigo.shiny.components.media.pdf;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyPdfComponentBuilder implements Builder<ShinyPdfComponent> {
    private String pdfTitle;
    private String path;
    private int page = 1; // Default to page 1
    private String componentHeight = "800px"; // Default height

    public ShinyPdfComponentBuilder withTitle(final String title) {
        this.pdfTitle = title;
        return this;
    }

    public ShinyPdfComponentBuilder withPath(final String pdfPath) {
        Assertion.check().isNotBlank(pdfPath);
        this.path = pdfPath;
        return this;
    }

    public ShinyPdfComponentBuilder withInitialPage(final int initialPage) {
        Assertion.check().isTrue(initialPage > 0, "Initial page must be a positive number");
        this.page = initialPage;
        return this;
    }

    public ShinyPdfComponentBuilder withHeight(final String height) {
        Assertion.check().isNotBlank(height);
        this.componentHeight = height;
        return this;
    }

    @Override
    public ShinyPdfComponent build() {
        return new ShinyPdfComponent(pdfTitle, path, page, componentHeight);
    }
}
