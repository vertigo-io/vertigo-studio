package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyTextPathTest {

	public static void main(final String[] args) {
		testBasicPaths();
		testCustomColors();
		testCustomSeparator();
		testSeparatorColor(); // New test case
		testEdgeCases();
	}

	private static void testBasicPaths() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Basic Paths ---" + ShinyColors.RESET);
		Shiny.textPath()
				.path("/home/user/documents/report.pdf")
				.print();
		Shiny.textPath()
				.path("C:\\Program Files\\Java\\jdk-17\\bin")
				.separator("\\")
				.print();
		Shiny.textPath()
				.path("folder/subfolder/file.txt")
				.print();
		System.out.println();
	}

	private static void testCustomColors() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Custom Colors ---" + ShinyColors.RESET);
		Shiny.textPath()
				.path("/app/config/settings.json")
				.rootColor(ShinyColors.RED)
				.nodeColor(ShinyColors.YELLOW)
				.leafColor(ShinyColors.GREEN)
				.print();
		Shiny.textPath()
				.path("/var/log/syslog")
				.rootColor(ShinyColors.MAGENTA)
				.nodeColor(ShinyColors.CYAN)
				.leafColor(ShinyColors.WHITE)
				.print();
		System.out.println();
	}

	private static void testCustomSeparator() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Custom Separator ---" + ShinyColors.RESET);
		Shiny.textPath()
				.path("data.csv")
				.separator(".")
				.rootColor(ShinyColors.RESET) // No root in this case
				.nodeColor(ShinyColors.BLUE)
				.leafColor(ShinyColors.RED)
				.print();
		Shiny.textPath()
				.path("item1->item2->item3")
				.separator("->")
				.rootColor(ShinyColors.RESET)
				.nodeColor(ShinyColors.GREEN)
				.leafColor(ShinyColors.YELLOW)
				.print();
		System.out.println();
	}

	private static void testSeparatorColor() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Separator Color ---" + ShinyColors.RESET);
		Shiny.textPath()
				.path("/path/to/my/file.txt")
				.separatorColor(ShinyColors.RED)
				.print();
		Shiny.textPath()
				.path("C:\\Users\\Admin\\Desktop")
				.separator("\\")
				.separatorColor(ShinyColors.BLUE)
				.print();
		System.out.println();
	}

	private static void testEdgeCases() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Edge Cases ---" + ShinyColors.RESET);
		Shiny.textPath()
				.path("/singlefile.txt")
				.print(); // Root and leaf
		Shiny.textPath()
				.path("justfile.txt")
				.print(); // Just leaf
		Shiny.textPath()
				.path("/")
				.print(); // Just root
		Shiny.textPath()
				.path("///a/b///c.txt")
				.print(); // Multiple separators
		System.out.println();
	}
}
