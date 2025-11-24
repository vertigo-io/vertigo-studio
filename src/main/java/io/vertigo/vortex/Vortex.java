package io.vertigo.vortex;

import java.io.File;

import io.vertigo.vortex.model.VXEntity;
import io.vertigo.vortex.model.VXFile;
import io.vertigo.vortex.reader.RawFileReader;

public class Vortex {
	public static String MODEL_BOOK = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\model-book.json";

	public static void main(final String[] args) throws Exception {
		final RawFileReader reader = new RawFileReader(new File(MODEL_BOOK));

		final VXFile model = reader.process();

		//-
		for (final VXEntity entity : model.entities()) {
			System.out.println(entity);
		}
	}

}
