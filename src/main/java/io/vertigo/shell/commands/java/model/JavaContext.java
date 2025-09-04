package io.vertigo.shell.commands.java.model;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;

public class JavaContext {
	private static JavaModel javaModel;

	public static void model(final JavaModel model) {
		Assertion.check().isNotNull(model);
		//---
		javaModel = model;
	}

	public static JavaModel model() {
		if (javaModel == null) {
			throw new VSystemException("The model must de loaded before usage");
		}
		return javaModel;
	}
}
