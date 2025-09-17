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
				.withPath("/home/user/documents/report.pdf")
				.render(writer);
		Shiny.textPath()
				.withPath("C:\\Program Files\\Java\\jdk-17\\bin")
				.withSeparator("\\")
				.render(writer);
		Shiny.textPath()
				.withPath("folder/subfolder/file.txt")
				.render(writer);
		writer.println();
	}

	private static void testCustomColors(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Custom Colors ---"));
		Shiny.textPath()
				.withPath("/app/config/settings.json")
				.withStyle(new ShinyTextPathStyle()
						.withRootColor(ShinyColors.RED)
						.withNodeColor(ShinyColors.YELLOW)
						.withLeafColor(ShinyColors.GREEN))
				.render(writer);
		Shiny.textPath()
				.withPath("/var/log/syslog")
				.withStyle(new ShinyTextPathStyle()
						.withRootColor(ShinyColors.MAGENTA)
						.withNodeColor(ShinyColors.CYAN)
						.withLeafColor(ShinyColors.WHITE))
				.render(writer);
		writer.println();
	}

	private static void testCustomSeparator(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Custom Separator ---"));
		Shiny.textPath()
				.withPath("data.csv")
				.withSeparator(".")
				.withStyle(new ShinyTextPathStyle()
						.withRootColor(ShinyColors.WHITE)
						.withNodeColor(ShinyColors.BLUE)
						.withLeafColor(ShinyColors.RED))
				.render(writer);
		Shiny.textPath()
				.withPath("item1->item2->item3")
				.withSeparator("->")
				.withStyle(new ShinyTextPathStyle()
						.withRootColor(ShinyColors.WHITE)
						.withNodeColor(ShinyColors.GREEN)
						.withLeafColor(ShinyColors.YELLOW))
				.render(writer);
		writer.println();
	}

	private static void testSeparatorColor(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Separator Color ---"));
		Shiny.textPath()
				.withPath("/path/to/my/file.txt")
				.withStyle(new ShinyTextPathStyle()
						.withSeparatorColor(ShinyColors.RED))
				.render(writer);
		Shiny.textPath()
				.withPath("C:\\Users\\Admin\\Desktop")
				.withSeparator("\\")
				.withStyle(new ShinyTextPathStyle()
						.withSeparatorColor(ShinyColors.BLUE))
				.render(writer);
		writer.println();
	}

	private static void testEdgeCases(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Edge Cases ---"));
		Shiny.textPath()
				.withPath("/singlefile.txt")
				.render(writer); // Root and leaf
		Shiny.textPath()
				.withPath("justfile.txt")
				.render(writer); // Just leaf
		Shiny.textPath()
				.withPath("/")
				.render(writer); // Just root
		Shiny.textPath()
				.withPath("///a/b///c.txt")
				.render(writer); // Multiple separators
		writer.println();
	}
}