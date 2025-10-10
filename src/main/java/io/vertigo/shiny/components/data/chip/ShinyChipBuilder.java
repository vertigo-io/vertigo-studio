package io.vertigo.shiny.components.data.chip;

public final class ShinyChipBuilder {
    private String _text;
    private String _color;
    private ShinyChipVariant _variant;
    private boolean _closable;
    private String _icon;

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
        return new ShinyChip(_text, _color, _variant, _closable, _icon);
    }
}
