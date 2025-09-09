package io.vertigo.shiny.components.text;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.text.textpath.ShinyTextPathStyle;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyTextPathTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicPaths(writer);
		testCustomColors(writer);
		testCustomSeparator(writer);
		testSeparatorColor(writer); // New test case
		testEdgeCases(writer);
	}

	private static void testBasicPaths(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Paths ---"));
		Shiny.textPath()
				.path("/home/user/documents/report.pdf")
				.render(writer);
		Shiny.textPath()
				.path("C:\\Program Files\\Java\\jdk-17\\bin")
				.separator("\\")
				.render(writer);
		Shiny.textPath()
				.path("folder/subfolder/file.txt")
				.render(writer);
		writer.println();
	}

	private static void testCustomColors(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Custom Colors ---"));
		Shiny.textPath()
				.path("/app/config/settings.json")
				.style(new ShinyTextPathStyle()
						.rootColor(ShinyColors.RED)
						.nodeColor(ShinyColors.YELLOW)
						.leafColor(ShinyColors.GREEN))
				.render(writer);
		Shiny.textPath()
				.path("/var/log/syslog")
				.style(new ShinyTextPathStyle()
						.rootColor(ShinyColors.MAGENTA)
						.nodeColor(ShinyColors.CYAN)
						.leafColor(ShinyColors.WHITE))
				.render(writer);
		writer.println();
	}

	private static void testCustomSeparator(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Custom Separator ---"));
		Shiny.textPath()
				.path("data.csv")
				.separator(".")
				.style(new ShinyTextPathStyle()
						.rootColor(ShinyColors.WHITE)
						.nodeColor(ShinyColors.BLUE)
						.leafColor(ShinyColors.RED))
				.render(writer);
		Shiny.textPath()
				.path("item1->item2->item3")
				.separator("->")
				.style(new ShinyTextPathStyle()
						.rootColor(ShinyColors.WHITE)
						.nodeColor(ShinyColors.GREEN)
						.leafColor(ShinyColors.YELLOW))
				.render(writer);
		writer.println();
	}

	private static void testSeparatorColor(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Separator Color ---"));
		Shiny.textPath()
				.path("/path/to/my/file.txt")
				.style(new ShinyTextPathStyle()
						.separatorColor(ShinyColors.RED))
				.render(writer);
		Shiny.textPath()
				.path("C:\\Users\\Admin\\Desktop")
				.separator("\\")
				.style(new ShinyTextPathStyle()
						.separatorColor(ShinyColors.BLUE))
				.render(writer);
		writer.println();
	}

	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Edge Cases ---"));
		Shiny.textPath()
				.path("/singlefile.txt")
				.render(writer); // Root and leaf
		Shiny.textPath()
				.path("justfile.txt")
				.render(writer); // Just leaf
		Shiny.textPath()
				.path("/")
				.render(writer); // Just root
		Shiny.textPath()
				.path("///a/b///c.txt")
				.render(writer); // Multiple separators
		writer.println();
	}
}
