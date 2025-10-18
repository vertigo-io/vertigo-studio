package io.vertigo.shiny.models.text.toggle;

public enum ShinyToggleType {
	TOGGLE("◉", "○"),
	CHECK("☑", "☐"),
	SWITCH("🔛", "🔴"),
	LIGHT("💡", "🌑"),
	BATTERY("🔋", "🪫"),
	STATUS("🟢", "🔴"),
	THUMBS("👍", "👎"),
	ARROW("▶", "⏸"),
	STAR("★", "☆"),
	CLASSIC("✅", "❌");

	private final String onIcon;
	private final String offIcon;

	ShinyToggleType(final String onIcon, final String offIcon) {
		this.onIcon = onIcon;
		this.offIcon = offIcon;
	}

	public String getOnIcon() {
		return onIcon;
	}

	public String getOffIcon() {
		return offIcon;
	}
}
