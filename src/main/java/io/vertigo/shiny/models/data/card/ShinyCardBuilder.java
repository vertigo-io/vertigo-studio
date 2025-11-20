package io.vertigo.shiny.models.data.card;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyCardBuilder implements Builder<ShinyCard> {
	private UUID _id;
	private String _title;
	private String _subtitle;
	private String _description;
	private String _imageUrl;
	private String _imageAlt;
	private String _link;
	private String _icon;
	private String _badgeLabel;
	private String _badgeColor;
	private ShinyCardFormat _format = ShinyCardFormat.M; // Default format

	public ShinyCardBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyCardBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this._title = title;
		return this;
	}

	public ShinyCardBuilder withSubtitle(@Nonnull final String subtitle) {
		Assertion.check().isNotBlank(subtitle);
		//---
		this._subtitle = subtitle;
		return this;
	}

	public ShinyCardBuilder withDescription(final String description) {
		this._description = description;
		return this;
	}

	public ShinyCardBuilder withImageUrl(final String imageUrl) {
		this._imageUrl = imageUrl;
		return this;
	}

	public ShinyCardBuilder withImageAlt(final String imageAlt) {
		this._imageAlt = imageAlt;
		return this;
	}

	public ShinyCardBuilder withLink(final String link) {
		this._link = link;
		return this;
	}

	public ShinyCardBuilder withIcon(final String icon) {
		this._icon = icon;
		return this;
	}

	public ShinyCardBuilder withBadge(@Nonnull final String label, final String color) {
		Assertion.check().isNotBlank(label);
		this._badgeLabel = label;
		this._badgeColor = color;
		return this;
	}

	public ShinyCardBuilder withFormat(@Nonnull final ShinyCardFormat format) {
		Assertion.check().isNotNull(format);
		this._format = format;
		return this;
	}

	@Override
	public ShinyCard build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyCard(
				_id,
				_title,
				_subtitle,
				_description,
				_imageUrl,
				_imageAlt,
				_link,
				_icon,
				_badgeLabel,
				_badgeColor,
				_format);
	}
}
