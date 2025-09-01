package io.vertigo.shell.shiny;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import io.vertigo.shell.shiny.barchart.ShinySortMode;
import io.vertigo.shell.shiny.figlet.ShinyFigletFonts;
import io.vertigo.shell.shiny.status.ShinyStatus.StatusShape;
import io.vertigo.shell.shiny.status.ShinyStatus.StatusType;
import io.vertigo.shell.shiny.table.ShinyBorder;
import io.vertigo.shell.shiny.tree.ShinyIcon;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyAllComponentsTest {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(final String[] args) {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Starting All Shiny Components Test ---" + ShinyColors.RESET);
		System.out.println("Press Enter to see the next component.");
		System.out.println();

		testBarChart();
		testCalendar();
		testFiglet();
		testGauge();
		testProgressBar();
		testSparkline();
		testSpinner();
		testStatus();
		testTable();
		testTree();
		testTextPath();
		testJson();

		System.out.println(ShinyColors.BLUE_BRIGHT + "--- All Shiny Components Test Finished ---" + ShinyColors.RESET);
		scanner.close();
	}

	private static void waitForEnter() {
		System.out.println(ShinyColors.YELLOW + "Press Enter to continue..." + ShinyColors.RESET);
		scanner.nextLine();
		System.out.println();
	}

	private static void testBarChart() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyBarChart" + ShinyColors.RESET);
		System.out.println("Parameters: title='Monthly Sales', header=['Jan', 'Feb', 'Mar'], rows=[100, 120, 90], sort=VALUE_DESC, maxBarLength=50");
		Shiny.barChart()
				.title("Monthly Sales")
				.header("Jan", "Feb", "Mar")
				.rows(100, 120, 90)
				.sort(ShinySortMode.VALUE_DESC)
				.print(50);
		waitForEnter();
	}

	private static void testCalendar() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyCalendar" + ShinyColors.RESET);
		System.out.println("Parameters: year=2024, month=JULY, locale=FRENCH, highlight=[2024-07-14], border=ROUNDED");
		Shiny.calendar()
				.year(2024)
				.month(Month.JULY.getValue())
				.locale(Locale.FRENCH)
				.highlight(LocalDate.of(2024, Month.JULY, 14))
				.border(ShinyBorder.Rounded)
				.print();
		waitForEnter();
	}

	private static void testFiglet() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyFiglet" + ShinyColors.RESET);
		System.out.println("Parameters: text='Hello', font=BIG, color=BLUE");
		Shiny.figlet()
				.text("Hello")
				.font(ShinyFigletFonts.BIG)
				.color(ShinyColors.BLUE)
				.print();
		waitForEnter();
	}

	private static void testGauge() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyGauge" + ShinyColors.RESET);
		System.out.println("Parameters: title='Progress', value=75, max=100, color=GREEN");
		Shiny.gauge()
				.title("Progress")
				.value(75)
				.max(100)
				.color(ShinyColors.GREEN)
				.print();
		waitForEnter();
	}

	private static void testProgressBar() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyProgressBar" + ShinyColors.RESET);
		System.out.println("Parameters: total=100");
		final var progressBar = Shiny.progressBar().total(100);
		for (int i = 0; i <= 100; i += 10) {
			progressBar.setProgress(i);
			progressBar.print();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		progressBar.finish();
		waitForEnter();
	}

	private static void testSparkline() {
		System.out.println(ShinyColors.CYAN + "Component: ShinySparkline" + ShinyColors.RESET);
		System.out.println("Parameters: title='Data Trend', data=[10, 12, 15, 13, 11, 10, 9, 10, 12, 14, 16, 15], color=MAGENTA");
		Shiny.sparkline()
				.title("Data Trend")
				.data(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
				.color(ShinyColors.MAGENTA)
				.print();
		waitForEnter();
	}

	private static void testSpinner() {
		System.out.println(ShinyColors.CYAN + "Component: ShinySpinner" + ShinyColors.RESET);
		System.out.println("Parameters: messages='Loading...', 'Processing...'");
		try (var spinner = Shiny.spinner()) {
			spinner.send("Loading...");
			Thread.sleep(1500);
			spinner.send("Processing...");
			Thread.sleep(1500);
		} catch (Exception e) {
			/* ignore */ }
		waitForEnter();
	}

	private static void testStatus() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyStatus" + ShinyColors.RESET);
		System.out.println("Parameters: title='Build Status', statuses=[SUCCESS, WARNING, ERROR, INFO], shape=SQUARE");
		Shiny.status()
				.title("Build Status")
				.statuses(List.of(StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.INFO))
				.shape(StatusShape.SQUARE)
				.print();
		waitForEnter();
	}

	private static void testTable() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyTable" + ShinyColors.RESET);
		System.out.println("Parameters: title='Users', header=['Name', 'Age'], rows=[['John', '30'], ['Jane', '25']], border=ASCII");
		Shiny.table()
				.title("Users")
				.header("Name", "Age")
				.rows(List.of(new String[] { "John", "30" }, new String[] { "Jane", "25" }))
				.beginStyle().border(ShinyBorder.Ascii).endStyle()
				.print();
		waitForEnter();
	}

	private static void testTree() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyTree" + ShinyColors.RESET);
		System.out.println("Parameters: label='Files', nodes=['src', 'main', 'file.txt'] with icons");
		final var tree = Shiny.tree("Files");
		tree.getRoot().addChild("src", ShinyIcon.FOLDER_OPEN)
				.addChild("main", ShinyIcon.FOLDER_OPEN)
				.addChild("file.txt", ShinyIcon.FILE);
		tree.print();
		waitForEnter();
	}

	private static void testTextPath() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyTextPath" + ShinyColors.RESET);
		System.out.println("Parameters: path='/home/user/documents/report.pdf', custom colors");
		Shiny.textPath()
				.path("C:/home/user/documents/report.pdf")
				.rootColor(ShinyColors.RED)
				.nodeColor(ShinyColors.YELLOW)
				.leafColor(ShinyColors.GREEN)
				.print();
		waitForEnter();
	}

	private static void testJson() {
		System.out.println(ShinyColors.CYAN + "Component: ShinyJson" + ShinyColors.RESET);
		System.out.println("Parameters: json='{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true}'");
		final String json = "{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true, \"address\": null}";
		Shiny.json()
				.json(json)
				.print();
		waitForEnter();
	}
}
