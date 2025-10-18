package io.vertigo.shiny.models.data.chip;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyChipBuilder implements Builder<ShinyChip> {
	private UUID _id;
    private String _text;
    private String _color;
    private ShinyChipVariant _variant;
    private boolean _closable;
    private String _icon;

	public ShinyChipBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

    public ShinyChipBuilder withText(final String text) {
        this._text = text;
        return this;
    }

    public ShinyChipBuilder withColor(final String color) {
        this._color = color;
        return this;
    }

    public ShinyChipBuilder withVariant(final ShinyChipVariant variant) {
        this._variant = variant;
        return this;
    }

    public ShinyChipBuilder withClosable(final boolean closable) {
        this._closable = closable;
        return this;
    }

    public ShinyChipBuilder withIcon(final String icon) {
        this._icon = icon;
        return this;
    }

    public ShinyChip build() {
		_id = _id == null ? UUID.randomUUID() : _id;
        return new ShinyChip(_id, _text, _color, _variant, _closable, _icon);
    }
}
