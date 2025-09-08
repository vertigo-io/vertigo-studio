package io.vertigo.shiny.components.text.markdown;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.text.paragraph.ShinyParagraph;

public final class ShinyMarkDown implements ShinyComponent {
	private final Shiny shiny;
	private String markdownText;

	public ShinyMarkDown(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyMarkDown fromFile(final String path) {
		try {
			this.markdownText = Files.readString(Path.of(path));
		} catch (final IOException e) {
			throw new RuntimeException("Error reading file: " + path, e);
		}
		return this;
	}

	public ShinyMarkDown fromText(final String text) {
		this.markdownText = text;
		return this;
	}

	@Override
	public void print() {
		Assertion.check().isNotNull(markdownText, "Markdown text not set. Use fromFile() or fromText().");
		//---
		final String[] lines = markdownText.split("\\R");
		for (int i = 0; i < lines.length; i++) {
			final String line = lines[i];
			if (line.startsWith("#")) {
				printTitle(line);
			} else if (line.startsWith("* ") || line.startsWith("- ") || line.matches("^\\d+\\.\\s.*")) {
				i = printList(lines, i);
			} else if (line.startsWith("|")) {
				i = printTable(lines, i);
			} else if (!line.trim().isEmpty()) {
				new ShinyParagraph(shiny, line).print();
			}
		}
	}

	private void printTitle(final String line) {
		int level = 0;
		while (level < line.length() && line.charAt(level) == '#') {
			level++;
		}
		final String titleText = line.substring(level).trim();
		Shiny.title().level(level).text(titleText).print();
	}

	private int printList(final String[] lines, int currentIndex) {
		String line = lines[currentIndex];
		while (currentIndex < lines.length && (line.startsWith("* ") || line.startsWith("- ") || line.matches("^\\d+\\.\\s.*"))) {
			String item = line.replaceAll("^([*\\-]\\d+\\.)\\s", "");
			Shiny.list().addItem(item);
			currentIndex++;
			if (currentIndex < lines.length) {
				line = lines[currentIndex];
			}
		}
		return currentIndex - 1;
	}

	private int printTable(final String[] lines, int currentIndex) {
		final List<String> header = parseTableRow(lines[currentIndex]);
		final List<String[]> rows = new ArrayList<>();
		currentIndex += 2; // Skip header and separator line
		while (currentIndex < lines.length && lines[currentIndex].startsWith("|")) {
			rows.add(parseTableRow(lines[currentIndex]).toArray(new String[0]));
			currentIndex++;
		}
		Shiny.table().header(header.toArray(new String[0])).rows(rows).print();
		return currentIndex - 1;
	}

	private List<String> parseTableRow(final String line) {
		final List<String> cells = new ArrayList<>();
		final String[] parts = line.split("\\|");
		for (int i = 1; i < parts.length; i++) {
			cells.add(parts[i].trim());
		}
		return cells;
	}
}
