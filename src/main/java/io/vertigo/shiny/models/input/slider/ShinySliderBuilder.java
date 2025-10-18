package io.vertigo.shiny.models.input.slider;

public final class ShinySliderBuilder {
	private String _label;
	private int _min;
	private int _max;
	private int _step;
	private int _value;
	private String _color;
	private boolean _thumbLabel;

	public ShinySliderBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
	public ShinySliderBuilder withLabel(final String label) {
		this._label = label;
		return this;
	}

	public ShinySliderBuilder withMin(final int min) {
		this._min = min;
		return this;
	}

	public ShinySliderBuilder withMax(final int max) {
		this._max = max;
		return this;
	}

	public ShinySliderBuilder withStep(final int step) {
		this._step = step;
		return this;
	}

	public ShinySliderBuilder withValue(final int value) {
		this._value = value;
		return this;
	}

	public ShinySliderBuilder withColor(final String color) {
		this._color = color;
		return this;
	}

	public ShinySliderBuilder withThumbLabel(final boolean thumbLabel) {
		this._thumbLabel = thumbLabel;
		return this;
	}

	public ShinySlider build() {
		_id = _id == null ? UUID.randomUUID() : _id;
	}
}
