package io.vertigo.vortex;

import java.io.File;

import io.vertigo.vortex.model.VXModel;
import io.vertigo.vortex.model.modules.VXModule;
import io.vertigo.vortex.reader.RawFileReader;

public class Vortex {
	public static String MODEL_BOOK = "C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\test\\java\\io\\vertigo\\vortex\\model\\model-book.json";

	public static void main(final String[] args) throws Exception {
		final RawFileReader reader = new RawFileReader(new File(MODEL_BOOK));

		final VXModel model = reader.process();
		//---
		for (final VXModule module : model.modules()) {
			System.out.println(module);
		}
	}

}
