package io.vertigo.shiny.models.media.pdf;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyPdfComponentBuilder implements Builder<ShinyPdfComponent> {
	private UUID _id;
    private String _title;
    private String _path;
    private int _page = 1; // Default to _page 1
    private String _componentHeight = "800px"; // Default height

	public ShinyPdfComponentBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

    public ShinyPdfComponentBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
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
		_id = _id == null ? UUID.randomUUID() : _id;
        return new ShinyPdfComponent(_id, _title, _path, _page, _componentHeight);
    }
}
