package io.vertigo.shell.shiny.toggle;

public enum ShinyToggleStyle {
	TOGGLE("◉", "○"),
	CHECK("☑", "☐"),
	SWITCH("🔛", "🔴"),
	LIGHT("💡", "🌑"),
	BATTERY("🔋", "🪫"),
	STATUS("🟢", "🔴"),
	THUMBS("👍", "👎"),
	ARROW("▶", "⏸"),
	STAR("⭐", "☆"),
	CLASSIC("✅", "❌");

	private final String onIcon;
	private final String offIcon;

	ShinyToggleStyle(final String onIcon, final String offIcon) {
		this.onIcon = onIcon;
		this.offIcon = offIcon;
	}

	String getOnIcon() {
		return onIcon;
	}

	String getOffIcon() {
		return offIcon;
	}
}
