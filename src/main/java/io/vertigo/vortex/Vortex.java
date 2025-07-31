package io.vertigo.vortex;

import java.io.File;

import io.vertigo.vortex.model.Entity;
import io.vertigo.vortex.model.Model;
import io.vertigo.vortex.reader.ModelReader;

public class Vortex {
	public static String MODEL_BOOK = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\model-book.json";

	public static void main(String[] args) throws Exception {
		ModelReader modelReader = new ModelReader(new File(MODEL_BOOK));

		final Model model = modelReader.process();

		//-
		for (Entity entity : model.entities) {
			System.out.println(entity);
		}
	}

}
