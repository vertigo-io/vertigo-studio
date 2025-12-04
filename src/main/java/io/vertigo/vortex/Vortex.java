package io.vertigo.vortex;

import java.io.File;
import java.util.List;

import io.vertigo.vortex.bronze.NotebookConfig;
import io.vertigo.vortex.gold.VXNotebook;
import io.vertigo.vortex.reader.BronzeToSilver;

public class Vortex {
	public static String MODEL_BOOK = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\module-book.json";
	public static String MODEL_AUTHOR = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\module-author.json";
	public static String LIBRARY_CORE = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\library-core.json";

	public static void main(final String[] args) throws Exception {
		final NotebookConfig notebookConfig = new NotebookConfig(
				List.of(new File(LIBRARY_CORE)),
				List.of(new File(MODEL_AUTHOR), new File(MODEL_BOOK)));

		final VXNotebook notebook = BronzeToSilver
				.from(notebookConfig)
				.toSilver()
				.toGold();

		System.out.println(notebook);
	}
}
