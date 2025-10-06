package io.vertigo.shiny.components.media.pdf;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyPdfComponentBuilder implements Builder<ShinyPdfComponent> {
    private String _title;
    private String _path;
    private int _page = 1; // Default to _page 1
    private String _componentHeight = "800px"; // Default height

    public ShinyPdfComponentBuilder withTitle(final String title) {
        this._title = title;
        return this;
    }

    public ShinyPdfComponentBuilder withPath(final String pdfPath) {
        Assertion.check().isNotBlank(pdfPath);
        this._path = pdfPath;
        return this;
    }

    public ShinyPdfComponentBuilder withInitialPage(final int initialPage) {
        Assertion.check().isTrue(initialPage > 0, "Initial _page must be a positive number");
        this._page = initialPage;
        return this;
    }

    public ShinyPdfComponentBuilder withHeight(final String height) {
        Assertion.check().isNotBlank(height);
        this._componentHeight = height;
        return this;
    }

    @Override
    public ShinyPdfComponent build() {
        return new ShinyPdfComponent(_title, _path, _page, _componentHeight);
    }
}
