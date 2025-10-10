package io.vertigo.shiny.components.feedback.alert;

public final class ShinyAlertBuilder {
    private ShinyAlertType _alertType = ShinyAlertType.INFO;
    private String _title;
    private String _content;
    private boolean _closable;
    private String _icon;
    private boolean _prominent;

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
        return new ShinyAlert(_alertType, _title, _content, _closable, _icon, _prominent);
    }
}
