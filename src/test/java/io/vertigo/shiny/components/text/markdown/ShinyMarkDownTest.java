package io.vertigo.shiny.components.text.markdown;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyMarkDownTest {

	public static void main(final String[] args) throws IOException {
		testPrintTitle();
		testPrintParagraph();
		testPrintList();
		testPrintTable();
		testFromFile();
	}

	private static void testPrintTitle() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Testing Title ---"));
		Shiny.markdown().fromText("# Title 1").render();
		Shiny.markdown().fromText("## Title 2").render();
		Shiny.markdown().fromText("### Title 3").render();
	}

	private static void testPrintParagraph() {
		System.out.println("--- Testing Paragraph ---");
		Shiny.markdown().fromText("This is a paragraph.").render();
		Shiny.markdown().fromText("This is a paragraph " + ShinyColors.BLUE.fg("with blue words")).render();
	}

	private static void testPrintList() {
		System.out.println("--- Testing List ---");
		Shiny.markdown().fromText(
				"""
						* Item 1
						* Item 2
						* Item
							* Sub-item 3a
							* Sub-item 3b
						""").render();

		System.out.println("--- Testing Ordered List ---");
		Shiny.markdown().fromText("""
				1. item 1
				2. item 2
				""").render();
	}

	private static void testPrintTable() {
		System.out.println("--- Testing Table ---");
		Shiny.markdown().fromText("| h1 | h2 |\n|---|---|\n| c1 | c2 |").render();
	}

	private static void testFromFile() throws IOException {
		System.out.println("--- Testing From File ---");
		final Path tempFile = Files.createTempFile("test", ".md");
		Files.writeString(tempFile, "# Title from file");

		Shiny.markdown().fromFile(tempFile.toString()).render();

		Files.delete(tempFile);
	}
}
