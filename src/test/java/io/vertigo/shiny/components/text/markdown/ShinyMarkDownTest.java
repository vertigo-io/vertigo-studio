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
		Shiny.markdown().fromText("# Title 1").print();
		Shiny.markdown().fromText("## Title 2").print();
		Shiny.markdown().fromText("### Title 3").print();
	}

	private static void testPrintParagraph() {
		System.out.println("--- Testing Paragraph ---");
		Shiny.markdown().fromText("This is a paragraph.").print();
		Shiny.markdown().fromText("This is a paragraph " + ShinyColors.BLUE.fg("with blue words")).print();
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
						""").print();

		System.out.println("--- Testing Ordered List ---");
		Shiny.markdown().fromText("""
				1. item 1
				2. item 2
				""").print();
	}

	private static void testPrintTable() {
		System.out.println("--- Testing Table ---");
		Shiny.markdown().fromText("| h1 | h2 |\n|---|---|\n| c1 | c2 |").print();
	}

	private static void testFromFile() throws IOException {
		System.out.println("--- Testing From File ---");
		final Path tempFile = Files.createTempFile("test", ".md");
		Files.writeString(tempFile, "# Title from file");

		Shiny.markdown().fromFile(tempFile.toString()).print();

		Files.delete(tempFile);
	}
}
