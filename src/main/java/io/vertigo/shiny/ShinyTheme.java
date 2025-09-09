package io.vertigo.shiny;

import java.text.NumberFormat;
import java.util.Locale;

import io.vertigo.shell.ShellContext;
import io.vertigo.shiny.components.data.json.ShinyJsonStyle;
import io.vertigo.shiny.components.data.list.ShinyListStyle;
import io.vertigo.shiny.components.data.table.ShinyTableStyle;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChartStyle;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGaugeStyle;
import io.vertigo.shiny.components.dataviz.rating.ShinyRatingStyle;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusStyle;
import io.vertigo.shiny.components.text.figlet.ShinyFigletStyle;
import io.vertigo.shiny.components.text.textpath.ShinyTextPathStyle;
import io.vertigo.shiny.components.text.toggle.ShinyToggleStyle;

public final class ShinyTheme {
	private boolean asciiTheme = false; //vs unicode
	private boolean squareTheme = false; //vs rounded
	//Values by default
	public final NumberFormat numberFormat = NumberFormat.getNumberInstance(ShellContext.LOCALE);
	private Locale locale = ShellContext.LOCALE;

	ShinyTheme() {
		//Theme must be obtained by Shiny.
	}

	public ShinyTheme ascii(boolean ascii) {
		asciiTheme = ascii;
		return this;
	}

	public ShinyTheme unicode(boolean unicode) {
		asciiTheme = !unicode;
		return this;
	}

	public boolean ascii() {
		return asciiTheme;
	}

	public ShinyTheme square(boolean square) {
		squareTheme = square;
		return this;
	}

	public ShinyTheme rounded(boolean rounded) {
		squareTheme = !rounded;
		return this;
	}

	public boolean square() {
		return squareTheme;
	}

	public NumberFormat numberFormat() {
		return numberFormat;
	}

	public Locale locale() {
		return locale;
	}

	private final ShinyTableStyle tableStyle = new ShinyTableStyle();
	private final ShinyTableStyle calendarStyle = new ShinyTableStyle();
	private final ShinyBarChartStyle barChartStyle = new ShinyBarChartStyle();
	private final ShinyJsonStyle jsonStyle = new ShinyJsonStyle();
	private final ShinyGaugeStyle gaugeStyle = new ShinyGaugeStyle();
	private final ShinyFigletStyle figletStyle = new ShinyFigletStyle();
	private final ShinyStatusStyle statusStyle = new ShinyStatusStyle();
	private final ShinyTextPathStyle textPathStyle = new ShinyTextPathStyle();
	private final ShinyToggleStyle toggleStyle = new ShinyToggleStyle();
	private final ShinyListStyle listStyle = new ShinyListStyle();
	private final ShinyRatingStyle ratingStyle = new ShinyRatingStyle();

	public ShinyTableStyle tableStyle() {
		return tableStyle;
	}

	public ShinyBarChartStyle barChartStyle() {
		return barChartStyle;
	}

	public ShinyJsonStyle jsonStyle() {
		return jsonStyle;
	}

	public ShinyGaugeStyle gaugeStyle() {
		return gaugeStyle;
	}

	public ShinyFigletStyle figletStyle() {
		return figletStyle;
	}

	public ShinyStatusStyle statusStyle() {
		return statusStyle;
	}

	public ShinyTableStyle calendarStyle() {
		return calendarStyle;
	}

	public ShinyTextPathStyle textPathStyle() {
		return textPathStyle;
	}

	public ShinyToggleStyle toggleStyle() {
		return toggleStyle;
	}

	public ShinyListStyle listStyle() {
		return listStyle;
	}

	public ShinyRatingStyle ratingStyle() {
		return ratingStyle;
	}
}
