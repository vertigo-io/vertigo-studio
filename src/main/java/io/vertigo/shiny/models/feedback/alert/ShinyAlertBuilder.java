package io.vertigo.shiny.models.feedback.alert;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyAlertBuilder implements Builder<ShinyAlert> {
	private UUID _id;
    private ShinyAlertType _alertType = ShinyAlertType.INFO;
    private String _title;
    private String _content;
    private boolean _closable;
    private String _icon;
    private boolean _prominent;

	public ShinyAlertBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

    public ShinyAlertBuilder withAlertType(final ShinyAlertType alertType) {
        this._alertType = alertType;
        return this;
    }

    public ShinyAlertBuilder withTitle(final String title) {
        this._title = title;
        return this;
    }

    public ShinyAlertBuilder withContent(final String content) {
        this._content = content;
        return this;
    }

    public ShinyAlertBuilder withClosable(final boolean closable) {
        this._closable = closable;
        return this;
    }

    public ShinyAlertBuilder withIcon(final String icon) {
        this._icon = icon;
        return this;
    }

    public ShinyAlertBuilder withProminent(final boolean prominent) {
        this._prominent = prominent;
        return this;
    }

    public ShinyAlert build() {
		_id = _id == null ? UUID.randomUUID() : _id;
        return new ShinyAlert(_id, _alertType, _title, _content, _closable, _icon, _prominent);
    }
}
