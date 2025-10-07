package io.vertigo.shiny.components;

import static io.vertigo.shiny.renderers.ShinyIcon.FILE;
import static io.vertigo.shiny.renderers.ShinyIcon.FOLDER_OPEN;
import static io.vertigo.shiny.style.ShinyColors.BLUE_BRIGHT;
import static io.vertigo.shiny.style.ShinyColors.CYAN;
import static io.vertigo.shiny.style.ShinyColors.YELLOW;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Scanner;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.bar.ShinySortMode;
import io.vertigo.shiny.components.dataviz.status.ShinyStatusType;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelection;

public class ShinyAllComponentsTest {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();

		writer.println(BLUE_BRIGHT.fg("--- Starting All Shiny Components with their default style---"));
		waitForEnter(writer);
		//---core
		testContainer(writer);
		testError(writer);
		//---data
		testCalendar(writer);
		testJson(writer);
		testList(writer);
		testTable(writer);
		testTree(writer);
		//---dataviz
		testBarChart(writer);
		testGauge(writer);
		testRating(writer);
		testSparkline(writer);
		testStatus(writer);
		//---input
		testMultiSelection(writer);
		testInputText(writer);
		//---live
		testProgressBar(writer);
		testSpinner(writer);
		//---text
		testFiglet(writer);
		testParagraph(writer);
		testTextPath(writer);
		testTitle(writer);
		testToggle(writer);
		//

		//	testShinyMarkDown(writer);

		writer.println(BLUE_BRIGHT.fg("--- All Shiny Components Test Finished ---"));
		scanner.close();
	}

	private static void waitForEnter(final ShinyWriter writer) {
		writer.println(YELLOW.fg("Press Enter to continue..."));
		scanner.nextLine();
		writer.println();
	}

	private static void testMultiSelection(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyMultiSelection"))
				.println("Parameters: title='Choose your favorite fruits:', options=['Apple', 'Banana', 'Cherry'], rows=[100, 120, 90], sort=VALUE_DESC, maxBarLength=50");
		//---
		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.withTitle("Choose your favorite fruits:")
				.withOptions("Apple", "Banana", "Cherry")
				.build();

		Shiny.render(multiSelection);

		final List<String> selected = multiSelection.getSelectedOptions();
		writer.println(selected.toString());
	}

	private static void testBarChart(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyBarChart"))
				.println("Parameters: title='Monthly Sales', header=['Jan', 'Feb', 'Mar'], rows=[100, 120, 90], sort=VALUE_DESC, maxBarLength=50");
		//---
		Shiny.render(
				Shiny.barChart()
						.withTitle("Monthly Sales")
						.withHeader("Jan", "Feb", "Mar")
						.withValues(100, 120, 90)
						.withSort(ShinySortMode.VALUE_DESC)
						.build());
		waitForEnter(writer);
	}

	private static void testCalendar(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyCalendar"))
				.println("Parameters: year=2024, month=JULY, locale=FRENCH, highlight=[2024-07-14], tableBorder=ROUNDED");
		//---
		Shiny.render(
				Shiny.calendar()
						.withYear(2024)
						.withMonth(Month.JULY.getValue())
						.addHighlightedDate(LocalDate.of(2024, Month.JULY, 14))
						.build());
		waitForEnter(writer);
	}

	private static void testFiglet(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyFiglet"))
				.println("Parameters: text='Hello', font=BIG, color=BLUE");
		//---
		Shiny.render(
				Shiny.figlet()
						.withText("Hello")
						.build());
		waitForEnter(writer);
	}

	private static void testGauge(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyGauge"))
				.println("Parameters: title='Progress', value=75, maxValue=100, color=GREEN");
		//---
		Shiny.render(
				Shiny.gauge()
						.withTitle("Progress")
						.withValue(75)
						.withMaxValue(100)
						.build());
		waitForEnter(writer);
	}

	private static void testJson(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyJson"))
				.println("Parameters: json='{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true}'");
		//---
		final String json = """
				{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true, \"address\": null}
				""";
		Shiny.render(
				Shiny.json()
						.withJson(json)
						.build());
		waitForEnter(writer);
	}

	private static void testList(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyList"))
				.println("Parameters: title='Tasks', items=['Task 1', 'Task 2', 'Task 3']");
		//---
		Shiny.render(
				Shiny.list()
						.withTitle("Tasks")
						.addItem("Task 1")
						.addItem("Task 2")
						.addItem("Task 3")
						.build());
		waitForEnter(writer);
	}

	private static void testProgressBar(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyProgressBar"))
				.println("Parameters: total=100");
		//---
		try (var progressBar = Shiny.progressBar().withTotal(100).build().start()) {
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
		writer.println(CYAN.fg("Component: ShinyRating"))
				.println("Parameters: rating=4, maxValue=5");
		//---
		Shiny.render(
				Shiny.rating()
						.withValue(4)
						.withMaxValue(5)
						.build());
		waitForEnter(writer);
	}

	private static void testSparkline(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinySparkline"))
				.println("Parameters: title='Data Trend', data=[10, 12, 15, 13, 11, 10, 9, 10, 12, 14, 16, 15], color=MAGENTA");
		//---
		Shiny.render(
				Shiny.sparkline()
						.withTitle("Data Trend")
						.withValues(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
						.build());
		waitForEnter(writer);
	}

	private static void testSpinner(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinySpinner"))
				.println("Parameters: messages='Loading...', 'Processing...'");
		//---
		try (var spinner = Shiny.spinner().build().start()) {
			spinner.liveSend("Loading...");
			Thread.sleep(1000);
			spinner.liveSend("Processing...");
			Thread.sleep(1000);
		} catch (final Exception e) {
			/* ignore */ }
		waitForEnter(writer);
	}

	private static void testStatus(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyStatus"))
				.println("Parameters: title='Build Status', statuses=[SUCCESS, WARNING, ERROR, INFO], shape=SQUARE");
		//---
		Shiny.render(
				Shiny.status()
						.withTitle("Build Status")
						.addAllTypes(
								ShinyStatusType.SUCCESS,
								ShinyStatusType.WARNING,
								ShinyStatusType.ERROR,
								ShinyStatusType.INFO)
						.build());
		waitForEnter(writer);
	}

	private static void testTable(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyTable"))
				.println("Parameters: title='Users', header=['firstName', 'lastName', 'Age'], rows=[['John', 'doe', '30'], ['Jane', 'doe', '25']], tableBorder=ASCII");
		//---
		Shiny.render(
				Shiny.table()
						.withTitle("Users")
						.withHeader("FirstName", "LastName", "Age")
						.addAllRows(
								new String[] { "John", "doe", "30" },
								new String[] { "Jane", "doe", "25" })
						.build());
		waitForEnter(writer);
	}

	private static void testTextPath(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyTextPath"))
				.println("Parameters: path='/home/user/documents/report.pdf', custom colors");
		//---
		Shiny.render(
				Shiny.textPath()
						.withPath("C:/home/user/documents/report.pdf")
						.build());
		waitForEnter(writer);
	}

	private static void testTitle(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyTitle"))
				.println("text: title='My Awesome Title', color=MAGENTA");
		//---
		Shiny.render(
				Shiny.title()
						.withText("My Awesome Title")
						.build());
		waitForEnter(writer);
	}

	private static void testToggle(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyToggle"))
				.println("Parameters: label='Enable Feature', enabled=true");
		//---
		Shiny.render(
				Shiny.toggle()
						.withLabel("Enable Feature")
						.withValue(true)
						.build());
		waitForEnter(writer);
	}

	private static void testTree(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyTree"))
				.println("Parameters: label='Files', nodes=['src', 'main', 'file.txt'] with icons");
		//---
		final var tree = Shiny.tree("Files").build();
		tree.getRoot().addChild("src", FOLDER_OPEN)
				.addChild("main", FOLDER_OPEN)
				.addChild("file.txt", FILE);
		Shiny.render(tree);
		waitForEnter(writer);
	}

	private static void testInputText(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyInputText"))
				.println("Parameters: label='Enter your name'");
		//---
		Shiny.render(
				Shiny.inputText()
						.withLabel("Enter your name")
						.build());
		waitForEnter(writer);
	}

	private static void testParagraph(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyParagraph"))
				.println("Parameters: text='This is a simple paragraph.'");
		//---
		Shiny.render(
				Shiny.paragraph()
						.withText("This is a simple paragraph.")
						.build());
		waitForEnter(writer);
	}

	private static void testError(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyError"))
				.println("Parameters: text='This is an amazing error.'");
		//---
		Shiny.render(
				Shiny.error()
						.withText("This is an amazing error.")
						.build());
		waitForEnter(writer);
	}

	private static void testContainer(final ShinyWriter writer) {
		writer.println(CYAN.fg("Component: ShinyContainer"))
				.println("Parameters: components=[table, paragraph]");
		//---
		Shiny.render(
				Shiny.container()
						.addComponent(
								Shiny.table()
										.withTitle("Users")
										.withHeader("FirstName", "LastName", "Age")
										.addAllRows(
												new String[] { "John", "doe", "30" },
												new String[] { "Jane", "doe", "25" })
										.build())
						.addComponent(Shiny.paragraph()
								.withText("This is a simple paragraph.")
								.build())
						.build());
		waitForEnter(writer);
	}

	//	private static void testShinyMarkDown(final ShinyWriter writer) {
	//		writer.println(CYAN.fg("Component: ShinyMarkDown"))
	//				.println("Parameters: markdown text with title, list, and table");
	//		//---
	//		final String markdown = "# Title\n* item 1\n* item 2\n| h1 | h2 |\n|---|---|\n| c1 | c2 |";
	//		Shiny.markdown()
	//				.withText(markdown)
	//				.build()
	//				.render(writer);
	//		waitForEnter(writer);
	//	}
}
