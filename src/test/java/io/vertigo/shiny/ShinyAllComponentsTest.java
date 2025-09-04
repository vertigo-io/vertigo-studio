package io.vertigo.shiny;

import static io.vertigo.shiny.color.ShinyColors.BLUE;
import static io.vertigo.shiny.color.ShinyColors.CYAN;
import static io.vertigo.shiny.color.ShinyColors.GREEN;
import static io.vertigo.shiny.color.ShinyColors.MAGENTA;
import static io.vertigo.shiny.color.ShinyColors.RED;
import static io.vertigo.shiny.color.ShinyColors.RESET;
import static io.vertigo.shiny.color.ShinyColors.YELLOW;
import static io.vertigo.shiny.components.data.tree.ShinyIcon.FILE;
import static io.vertigo.shiny.components.data.tree.ShinyIcon.FOLDER_OPEN;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import io.vertigo.shiny.components.data.table.ShinyBorder;
import io.vertigo.shiny.components.dataviz.barchart.ShinySortMode;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus.StatusShape;
import io.vertigo.shiny.components.dataviz.status.ShinyStatus.StatusType;
import io.vertigo.shiny.components.input.multiselection.ShinyMultiSelection;
import io.vertigo.shiny.components.text.figlet.ShinyFigletFonts;

public class ShinyAllComponentsTest {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(final String[] args) {
		System.out.println(BLUE.bright() + "--- Starting All Shiny Components Test ---" + RESET);
		System.out.println("Press Enter to see the next component.");
		System.out.println();

		testBarChart();
		testCalendar();
		testFiglet();
		testGauge();
		testInputText();
		testJson();
		testList();
		testMultiSelection();
		testProgressBar();
		testRating();
		testSparkline();
		testSpinner();
		testStatus();
		testTable();
		testTextPath();
		testTitle();
		testToggle();
		testTree();

		System.out.println(BLUE.bright() + "--- All Shiny Components Test Finished ---" + RESET);
		scanner.close();
	}

	private static void waitForEnter() {
		System.out.println(YELLOW + "Press Enter to continue..." + RESET);
		scanner.nextLine();
		System.out.println();
	}

	private static void testMultiSelection() {
		System.out.println(CYAN + "Component: ShinyMultiSelection" + RESET);
		System.out.println("Parameters: title='Choose your favorite fruits:', options=['Apple', 'Banana', 'Cherry'], rows=[100, 120, 90], sort=VALUE_DESC, maxBarLength=50");

		final ShinyMultiSelection multiSelection = Shiny.multiSelection()
				.title("Choose your favorite fruits:")
				.options("Apple", "Banana", "Cherry")
				.strict();

		multiSelection.print();

		final List<String> selected = multiSelection.getSelectedOptions();
		System.out.println(selected);
	}

	private static void testBarChart() {
		System.out.println(CYAN + "Component: ShinyBarChart" + RESET);
		System.out.println("Parameters: title='Monthly Sales', header=['Jan', 'Feb', 'Mar'], rows=[100, 120, 90], sort=VALUE_DESC, maxBarLength=50");
		Shiny.barChart()
				.title("Monthly Sales")
				.header("Jan", "Feb", "Mar")
				.rows(100, 120, 90)
				.sort(ShinySortMode.VALUE_DESC)
				.length(50)
				.print();
		waitForEnter();
	}

	private static void testCalendar() {
		System.out.println(CYAN + "Component: ShinyCalendar" + RESET);
		System.out.println("Parameters: year=2024, month=JULY, locale=FRENCH, highlight=[2024-07-14], border=ROUNDED");
		Shiny.calendar()
				.year(2024)
				.month(Month.JULY.getValue())
				.locale(Locale.FRENCH)
				.highlight(LocalDate.of(2024, Month.JULY, 14))
				.border(ShinyBorder.Normal)
				.print();
		waitForEnter();
	}

	private static void testFiglet() {
		System.out.println(CYAN + "Component: ShinyFiglet" + RESET);
		System.out.println("Parameters: text='Hello', font=BIG, color=BLUE");
		Shiny.figlet()
				.text("Hello")
				.font(ShinyFigletFonts.BIG)
				.color(BLUE)
				.print();
		waitForEnter();
	}

	private static void testGauge() {
		System.out.println(CYAN + "Component: ShinyGauge" + RESET);
		System.out.println("Parameters: title='Progress', value=75, max=100, color=GREEN");
		Shiny.gauge()
				.title("Progress")
				.value(75)
				.max(100)
				.color(GREEN)
				.print();
		waitForEnter();
	}

	private static void testJson() {
		System.out.println(CYAN + "Component: ShinyJson" + RESET);
		System.out.println("Parameters: json='{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true}'");
		final String json = """
				{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true, \"address\": null}
				""";
		Shiny.json()
				.json(json)
				.print();
		waitForEnter();
	}

	private static void testList() {
		System.out.println(CYAN + "Component: ShinyList" + RESET);
		System.out.println("Parameters: title='Tasks', items=['Task 1', 'Task 2', 'Task 3']");
		Shiny.list()
				.title("Tasks")
				.addItem("Task 1")
				.addItem("Task 2")
				.addItem("Task 3")
				.print();
		waitForEnter();
	}

	private static void testProgressBar() {
		System.out.println(CYAN + "Component: ShinyProgressBar" + RESET);
		System.out.println("Parameters: total=100");
		try (var progressBar = Shiny.progressBar().total(100).start()) {
			for (int i = 0; i <= 100; i += 10) {
				progressBar.liveUpdate(i + 1);
				try {
					Thread.sleep(50);
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		waitForEnter();
	}

	private static void testRating() {
		System.out.println(CYAN + "Component: ShinyRating" + RESET);
		System.out.println("Parameters: rating=4, max=5");
		Shiny.rating()
				.value(4)
				.maxValue(5)
				.print();
		waitForEnter();
	}

	private static void testSparkline() {
		System.out.println(CYAN + "Component: ShinySparkline" + RESET);
		System.out.println("Parameters: title='Data Trend', data=[10, 12, 15, 13, 11, 10, 9, 10, 12, 14, 16, 15], color=MAGENTA");
		Shiny.sparkline()
				.title("Data Trend")
				.data(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
				.color(MAGENTA)
				.print();
		waitForEnter();
	}

	private static void testSpinner() {
		System.out.println(CYAN + "Component: ShinySpinner" + RESET);
		System.out.println("Parameters: messages='Loading...', 'Processing...'");
		try (var spinner = Shiny.spinner().start()) {
			spinner.liveSend("Loading...");
			Thread.sleep(1000);
			spinner.liveSend("Processing...");
			Thread.sleep(1000);
		} catch (final Exception e) {
			/* ignore */ }
		waitForEnter();
	}

	private static void testStatus() {
		System.out.println(CYAN + "Component: ShinyStatus" + RESET);
		System.out.println("Parameters: title='Build Status', statuses=[SUCCESS, WARNING, ERROR, INFO], shape=SQUARE");
		Shiny.status()
				.title("Build Status")
				.statuses(List.of(StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.INFO))
				.shape(StatusShape.SQUARE)
				.print();
		waitForEnter();
	}

	private static void testTable() {
		System.out.println(CYAN + "Component: ShinyTable" + RESET);
		System.out.println("Parameters: title='Users', header=['Name', 'Age'], rows=[['John', '30'], ['Jane', '25']], border=ASCII");
		Shiny.table()
				.title("Users")
				.header("Name", "Age")
				.rows(
						new String[] { "John", "30" },
						new String[] { "Jane", "25" })
				.print();
		waitForEnter();
	}

	private static void testTextPath() {
		System.out.println(CYAN + "Component: ShinyTextPath" + RESET);
		System.out.println("Parameters: path='/home/user/documents/report.pdf', custom colors");
		Shiny.textPath()
				.path("C:/home/user/documents/report.pdf")
				.rootColor(RED)
				.nodeColor(YELLOW)
				.leafColor(GREEN)
				.print();
		waitForEnter();
	}

	private static void testTitle() {
		System.out.println(CYAN + "Component: ShinyTitle" + RESET);
		System.out.println("text: title='My Awesome Title', color=MAGENTA");
		Shiny.title()
				.text("My Awesome Title")
				.print();
		waitForEnter();
	}

	private static void testToggle() {
		System.out.println(CYAN + "Component: ShinyToggle" + RESET);
		System.out.println("Parameters: label='Enable Feature', enabled=true");
		Shiny.toggle()
				.label("Enable Feature")
				.value(true)
				.print();
		waitForEnter();
	}

	private static void testTree() {
		System.out.println(CYAN + "Component: ShinyTree" + RESET);
		System.out.println("Parameters: label='Files', nodes=['src', 'main', 'file.txt'] with icons");
		final var tree = Shiny.tree("Files");
		tree.getRoot().addChild("src", FOLDER_OPEN)
				.addChild("main", FOLDER_OPEN)
				.addChild("file.txt", FILE);
		tree.print();
		waitForEnter();
	}

	private static void testInputText() {
		System.out.println(CYAN + "Component: ShinyInputText" + RESET);
		System.out.println("Parameters: label='Enter your name'");
		Shiny.inputText().label("Enter your name").print();
		waitForEnter();
	}
}
