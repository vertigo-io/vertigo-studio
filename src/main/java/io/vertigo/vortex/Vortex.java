package io.vertigo.vortex;

import java.io.File;

import io.vertigo.vortex.model.VXEntity;
import io.vertigo.vortex.model.VXModel;
import io.vertigo.vortex.reader.ModelReader;

public class Vortex {
	public static String MODEL_BOOK = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\model-book.json";

	public static void main(final String[] args) throws Exception {
		final ModelReader modelReader = new ModelReader(new File(MODEL_BOOK));

		final VXModel model = modelReader.process();

		//-
		for (final VXEntity entity : model.entities()) {
			System.out.println(entity);
		}
	}

}
