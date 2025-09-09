package io.vertigo.shiny.components;

import static io.vertigo.shiny.components.data.tree.ShinyIcon.FILE;
import static io.vertigo.shiny.components.data.tree.ShinyIcon.FOLDER_OPEN;
import static io.vertigo.shiny.style.ShinyColors.BLUE;
import static io.vertigo.shiny.style.ShinyColors.BLUE_BRIGHT;
import static io.vertigo.shiny.style.ShinyColors.CYAN;
import static io.vertigo.shiny.style.ShinyColors.GREEN;
import static io.vertigo.shiny.style.ShinyColors.MAGENTA;
import static io.vertigo.shiny.style.ShinyColors.RED;
import static io.vertigo.shiny.style.ShinyColors.YELLOW;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.data.table.ShinyBorder;
import io.vertigo.shiny.components.dataviz.barchart.ShinySortMode;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus.StatusShape;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus.StatusType;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelection;
import io.vertigo.shiny.components.text.figlet.ShinyFigletFonts;

public class ShinyAllComponentsTest {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();

		writer.println(BLUE_BRIGHT.fg("--- Starting All Shiny Components Test ---"));
		writer.println("Press Enter to see the next component.");
		writer.println();

		testBarChart(writer);
		testCalendar(writer);
		testFiglet(writer);
		testGauge(writer);
		testInputText(writer);
		testJson(writer);
		testList(writer);
		testMultiSelection(writer);
		testProgressBar(writer);
		testRating(writer);
		testSparkline(writer);
		testSpinner(writer);
		testStatus(writer);
		testTable(writer);
		testTextPath(writer);
		testTitle(writer);
		testToggle(writer);
		testTree(writer);
		testShinyParagraph(writer);
		testShinyMarkDown(writer);

		writer.println(BLUE_BRIGHT.fg("--- All Shiny Components Test Finished ---"));
		scanner.close();
	}

	private static void waitForEnter(final ShinyWriter writer) {
		writer.println(YELLOW.fg("Press Enter to continue..."));
		scanner.nextLine();
		writer.println();
	}

	private static void testMultiSelection(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyMultiSelection"));
		writer.println("Parameters: title='Choose your favorite fruits:', options=['Apple', 'Banana', 'Cherry'], rows=[100, 120, 90], sort=VALUE_DESC, maxBarLength=50");

		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Choose your favorite fruits:")
				.options("Apple", "Banana", "Cherry")
				.strict();

		multiSelection.render(writer);

		final List<String> selected = multiSelection.getSelectedOptions();
		writer.println(selected.toString());
	}

	private static void testBarChart(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyBarChart"));
		writer.println("Parameters: title='Monthly Sales', header=['Jan', 'Feb', 'Mar'], rows=[100, 120, 90], sort=VALUE_DESC, maxBarLength=50");
		Shiny.barChart()
				.title("Monthly Sales")
				.header("Jan", "Feb", "Mar")
				.rows(100, 120, 90)
				.sort(ShinySortMode.VALUE_DESC)
				.length(50)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testCalendar(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyCalendar"));
		writer.println("Parameters: year=2024, month=JULY, locale=FRENCH, highlight=[2024-07-14], border=ROUNDED");
		Shiny.calendar()
				.year(2024)
				.month(Month.JULY.getValue())
				.locale(Locale.FRENCH)
				.highlight(LocalDate.of(2024, Month.JULY, 14))
				.border(ShinyBorder.Normal)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testFiglet(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyFiglet"));
		writer.println("Parameters: text='Hello', font=BIG, color=BLUE");
		Shiny.figlet()
				.text("Hello")
				.font(ShinyFigletFonts.BIG)
				.color(BLUE)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testGauge(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyGauge"));
		writer.println("Parameters: title='Progress', value=75, max=100, color=GREEN");
		Shiny.gauge()
				.title("Progress")
				.value(75)
				.max(100)
				.color(GREEN)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testJson(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyJson"));
		writer.println("Parameters: json='{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true}'");
		final String json = """
				{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true, \"address\": null}
				""";
		Shiny.json()
				.json(json)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testList(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyList"));
		writer.println("Parameters: title='Tasks', items=['Task 1', 'Task 2', 'Task 3']");
		Shiny.list()
				.title("Tasks")
				.addItem("Task 1")
				.addItem("Task 2")
				.addItem("Task 3")
				.render(writer);
		waitForEnter(writer);
	}

	private static void testProgressBar(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyProgressBar"));
		writer.println("Parameters: total=100");
		try (var progressBar = Shiny.progressBar().total(100).start(writer)) {
			for (int i = 0; i <= 100; i += 10) {
				progressBar.liveUpdate(i + 1);
				try {
					Thread.sleep(50);
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		waitForEnter(writer);
	}

	private static void testRating(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyRating"));
		writer.println("Parameters: rating=4, max=5");
		Shiny.rating()
				.value(4)
				.maxValue(5)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testSparkline(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinySparkline"));
		writer.println("Parameters: title='Data Trend', data=[10, 12, 15, 13, 11, 10, 9, 10, 12, 14, 16, 15], color=MAGENTA");
		Shiny.sparkline()
				.title("Data Trend")
				.data(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
				.color(MAGENTA)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testSpinner(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinySpinner"));
		writer.println("Parameters: messages='Loading...', 'Processing...'");
		try (var spinner = Shiny.spinner().start(writer)) {
			spinner.liveSend("Loading...");
			Thread.sleep(1000);
			spinner.liveSend("Processing...");
			Thread.sleep(1000);
		} catch (final Exception e) {
			/* ignore */ }
		waitForEnter(writer);
	}

	private static void testStatus(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyStatus"));
		writer.println("Parameters: title='Build Status', statuses=[SUCCESS, WARNING, ERROR, INFO], shape=SQUARE");
		Shiny.status()
				.title("Build Status")
				.statuses(List.of(StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.INFO))
				.shape(StatusShape.SQUARE)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testTable(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyTable"));
		writer.println("Parameters: title='Users', header=['firstName', 'lastName', 'Age'], rows=[['John', 'doe', '30'], ['Jane', 'doe', '25']], border=ASCII");
		Shiny.table()
				.title("Users")
				.header("FirstName", "LastName", "Age")
				.rows(
						new String[] { "John", "doe", "30" },
						new String[] { "Jane", "doe", "25" })
				.render(writer);
		waitForEnter(writer);
	}

	private static void testTextPath(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyTextPath"));
		writer.println("Parameters: path='/home/user/documents/report.pdf', custom colors");
		Shiny.textPath()
				.path("C:/home/user/documents/report.pdf")
				.rootColor(RED)
				.nodeColor(YELLOW)
				.leafColor(GREEN)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testTitle(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyTitle"));
		writer.println("text: title='My Awesome Title', color=MAGENTA");
		Shiny.title()
				.text("My Awesome Title")
				.render(writer);
		waitForEnter(writer);
	}

	private static void testToggle(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyToggle"));
		writer.println("Parameters: label='Enable Feature', enabled=true");
		Shiny.toggle()
				.label("Enable Feature")
				.value(true)
				.render(writer);
		waitForEnter(writer);
	}

	private static void testTree(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyTree"));
		writer.println("Parameters: label='Files', nodes=['src', 'main', 'file.txt'] with icons");
		final var tree = Shiny.tree("Files");
		tree.getRoot().addChild("src", FOLDER_OPEN)
				.addChild("main", FOLDER_OPEN)
				.addChild("file.txt", FILE);
		tree.render(writer);
		waitForEnter(writer);
	}

	private static void testInputText(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyInputText"));
		writer.println("Parameters: label='Enter your name'");
		Shiny.inputText()
				.label("Enter your name")
				.render(writer);
		waitForEnter(writer);
	}

	private static void testShinyParagraph(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyParagraph"));
		writer.println("Parameters: text='This is a simple paragraph.'");
		Shiny.paragraph()
				.text("This is a simple paragraph.")
				.render(writer);
		waitForEnter(writer);
	}

	private static void testShinyMarkDown(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyMarkDown"));
		writer.println("Parameters: markdown text with title, list, and table");
		final String markdown = "# Title\n* item 1\n* item 2\n| h1 | h2 |\n|---|---|\n| c1 | c2 |";
		Shiny.markdown()
				.fromText(markdown)
				.render(writer);
		waitForEnter(writer);
	}
}
