package io.vertigo.vortex.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.model.VXNotebook;
import io.vertigo.vortex.raw.RawNotebook;
import io.vertigo.vortex.raw.library.RawLibrarySource;
import io.vertigo.vortex.raw.module.RawModuleSource;

/**
 * Reads a model from a JSON file.
 * The reader first validates the JSON file against a schema, then reads it into a raw model,
 * and finally transforms the raw model into a VXModel.
 * @synthetic
 */
public record RawSourceReader(Notebook notebook) {

	public RawSourceReader {
		Assertion.check().isNotNull(notebook);
	}

	/**
	 * Processes the JSON file and returns the model.
	 * @return the processed model
	 * @throws Exception if an error occurs during validation, reading, or transformation
	 */
	public VXNotebook process() throws Exception {
		//- STEP 1
		validate(notebook);

		//- STEP 2
		final RawNotebook rawNotebook = read(notebook);

		//- STEP 3
		return new RawToGold(rawNotebook)
				.transform();
	}

	private static void validate(Notebook notebook) throws Exception {
		for (File file : notebook.libraries()) {
			validateLibrary(file);
		}
		//---
		for (File file : notebook.modules()) {
			validateModule(file);
		}
	}

	private static RawNotebook read(Notebook notebook) throws Exception {
		List<RawLibrarySource> rawLibrarySources = new ArrayList<>();
		for (File file : notebook.libraries()) {
			rawLibrarySources.add(readLibrary(file));
		}
		//---
		List<RawModuleSource> rawModuleSources = new ArrayList<>();
		for (File file : notebook.modules()) {
			rawModuleSources.add(readModule(file));
		}
		return new RawNotebook(rawLibrarySources, rawModuleSources);
	}

	private static void validateModule(File file) throws Exception {
		final File schemaFile = new File("C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\main\\java\\io\\vertigo\\vortex\\raw\\module\\raw-module-schema.json");
		JsonValidator.validate(schemaFile, file);
	}

	private static void validateLibrary(File file) throws Exception {
		final File schemaFile = new File("C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\main\\java\\io\\vertigo\\vortex\\raw\\library\\raw-library-schema.json");
		JsonValidator.validate(schemaFile, file);
	}

	private static RawModuleSource readModule(File file) throws IOException {
		Assertion.check().isNotNull(file);
		//---
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(file, RawModuleSource.class);
	}

	private static RawLibrarySource readLibrary(File file) throws IOException {
		Assertion.check().isNotNull(file);
		//---
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(file, RawLibrarySource.class);
	}
}
