package io.vertigo.shiny;

import java.text.NumberFormat;
import java.util.Locale;

import io.vertigo.shell.ShellContext;
import io.vertigo.shiny.components.data.json.ShinyJsonStyle;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChartStyle;
import io.vertigo.shiny.components.dataviz.gauge.ShinyGaugeStyle;

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

	//	private final ShinyTableStyle tableStyle = new ShinyTableStyle();
	private final ShinyBarChartStyle barChartStyle = new ShinyBarChartStyle();
	//	private final ShinySparklineStyle sparklineStyle = new ShinySparklineStyle();
	private final ShinyJsonStyle jsonStyle = new ShinyJsonStyle();
	private final ShinyGaugeStyle gaugeStyle = new ShinyGaugeStyle();

	//
	//	public ShinyTableStyle tableStyle() {
	//		return tableStyle;
	//	}
	//
	public ShinyBarChartStyle barChartStyle() {
		return barChartStyle;
	}

	//	public ShinySparklineStyle sparklineStyle() {
	//		return sparklineStyle;
	//	}

	public ShinyJsonStyle jsonStyle() {
		return jsonStyle;
	}

	public ShinyGaugeStyle gaugeStyle() {
		return gaugeStyle;
	}
}
