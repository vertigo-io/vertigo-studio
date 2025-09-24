package io.vertigo.shiny;

import java.text.NumberFormat;
import java.util.Locale;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.ShellContext;
import io.vertigo.shiny.renderers.core.ShinyContainerStyle;
import io.vertigo.shiny.renderers.core.ShinyErrorStyle;
import io.vertigo.shiny.renderers.data.ShinyCalendarStyle;
import io.vertigo.shiny.renderers.data.ShinyJsonStyle;
import io.vertigo.shiny.renderers.data.ShinyListStyle;
import io.vertigo.shiny.renderers.data.ShinyTableStyle;
import io.vertigo.shiny.renderers.data.ShinyTreeStyle;
import io.vertigo.shiny.renderers.dataviz.ShinyBarChartStyle;
import io.vertigo.shiny.renderers.dataviz.ShinyGaugeStyle;
import io.vertigo.shiny.renderers.dataviz.ShinyRatingStyle;
import io.vertigo.shiny.renderers.dataviz.ShinySparklineStyle;
import io.vertigo.shiny.renderers.dataviz.ShinyStatusStyle;
import io.vertigo.shiny.renderers.input.ShinyInputTextStyle;
import io.vertigo.shiny.renderers.input.ShinyMultiSelectionStyle;
import io.vertigo.shiny.renderers.live.ShinyProgressBarStyle;
import io.vertigo.shiny.renderers.live.ShinySpinnerStyle;
import io.vertigo.shiny.renderers.text.ShinyFigletStyle;
import io.vertigo.shiny.renderers.text.ShinyParagraphStyle;
import io.vertigo.shiny.renderers.text.ShinyTextPathStyle;
import io.vertigo.shiny.renderers.text.ShinyTitleStyle;
import io.vertigo.shiny.renderers.text.ShinyToggleStyle;

public final class ShinyTheme {
	private boolean asciiTheme = false; //vs unicode
	private boolean squareTheme = false; //vs rounded
	//Values by default
	public final NumberFormat themeNumberFormat = NumberFormat.getNumberInstance(ShellContext.LOCALE);
	private Locale themeLocale = ShellContext.LOCALE;

	ShinyTheme() {
		//Theme must be obtained by Shiny.
	}

	public ShinyTheme withAscii(boolean ascii) {
		asciiTheme = ascii;
		return this;
	}

	public ShinyTheme withUnicode(boolean unicode) {
		asciiTheme = !unicode;
		return this;
	}

	public boolean ascii() {
		return asciiTheme;
	}

	public ShinyTheme withSquare(boolean square) {
		squareTheme = square;
		return this;
	}

	public ShinyTheme withRounded(boolean rounded) {
		squareTheme = !rounded;
		return this;
	}

	public boolean square() {
		return squareTheme;
	}

	public NumberFormat numberFormat() {
		return themeNumberFormat;
	}

	public void withLocale(Locale locale) {
		Assertion.check().isNotNull(locale);
		//---
		this.themeLocale = locale;
	}

	public Locale locale() {
		return themeLocale;
	}

	private final ShinyTableStyle tableStyle = new ShinyTableStyle();
	private final ShinyCalendarStyle calendarStyle = new ShinyCalendarStyle();
	private final ShinyBarChartStyle barChartStyle = new ShinyBarChartStyle();
	private final ShinyJsonStyle jsonStyle = new ShinyJsonStyle();
	private final ShinyGaugeStyle gaugeStyle = new ShinyGaugeStyle();
	private final ShinyFigletStyle figletStyle = new ShinyFigletStyle();
	private final ShinyStatusStyle statusStyle = new ShinyStatusStyle();
	private final ShinyTextPathStyle textPathStyle = new ShinyTextPathStyle();
	private final ShinyToggleStyle toggleStyle = new ShinyToggleStyle();
	private final ShinyListStyle listStyle = new ShinyListStyle();
	private final ShinyRatingStyle ratingStyle = new ShinyRatingStyle();
	private final ShinySparklineStyle sparklineStyle = new ShinySparklineStyle();
	private final ShinyProgressBarStyle progressBarStyle = new ShinyProgressBarStyle();
	private final ShinySpinnerStyle spinnerStyle = new ShinySpinnerStyle();
	private final ShinyTreeStyle treeStyle = new ShinyTreeStyle();
	private final ShinyParagraphStyle paragraphStyle = new ShinyParagraphStyle();
	private final ShinyTitleStyle titleStyle = new ShinyTitleStyle();
	private final ShinyInputTextStyle inputTextStyle = new ShinyInputTextStyle();
	private final ShinyMultiSelectionStyle multiSelectionStyle = new ShinyMultiSelectionStyle();
	private final ShinyErrorStyle errorStyle = new ShinyErrorStyle();
	private final ShinyContainerStyle containerStyle = new ShinyContainerStyle();

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

	public ShinyCalendarStyle calendarStyle() {
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

	public ShinySparklineStyle sparklineStyle() {
		return sparklineStyle;
	}

	public ShinyProgressBarStyle progressBarStyle() {
		return progressBarStyle;
	}

	public ShinySpinnerStyle spinnerStyle() {
		return spinnerStyle;
	}

	public ShinyTreeStyle treeStyle() {
		return treeStyle;
	}

	public ShinyParagraphStyle paragraphStyle() {
		return paragraphStyle;
	}

	public ShinyTitleStyle titleStyle() {
		return titleStyle;
	}

	public ShinyInputTextStyle inputTextStyle() {
		return inputTextStyle;
	}

	public ShinyMultiSelectionStyle multiSelectionStyle() {
		return multiSelectionStyle;
	}

	public ShinyErrorStyle errorStyle() {
		return errorStyle;
	}

	public ShinyContainerStyle containerStyle() {
		return containerStyle;
	}
}
