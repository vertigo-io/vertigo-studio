package io.vertigo.shiny.models.media.pdf;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyPdfBuilder implements Builder<ShinyPdf> {
	private UUID _id;
    private String _title;
    private String _path;
    private int _page = 1; // Default to _page 1
    private String _componentHeight = "800px"; // Default height

	public ShinyPdfBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

    public ShinyPdfBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
        this._title = title;
        return this;
    }

    public ShinyPdfBuilder withPath(final String pdfPath) {
        Assertion.check().isNotBlank(pdfPath);
        this._path = pdfPath;
        return this;
    }

    public ShinyPdfBuilder withInitialPage(final int initialPage) {
        Assertion.check().isTrue(initialPage > 0, "Initial _page must be a positive number");
        this._page = initialPage;
        return this;
    }

    public ShinyPdfBuilder withHeight(final String height) {
        Assertion.check().isNotBlank(height);
        this._componentHeight = height;
        return this;
    }

    @Override
    public ShinyPdf build() {
		_id = _id == null ? UUID.randomUUID() : _id;
        return new ShinyPdf(_id, _title, _path, _page, _componentHeight);
    }
}
