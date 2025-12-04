package io.vertigo.vortex.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.bronze.NotebookConfig;
import io.vertigo.vortex.silver.RawNotebook;
import io.vertigo.vortex.silver.library.RawLibrary;
import io.vertigo.vortex.silver.module.RawModule;

/**
 * Reads a rawNotebook (Silver) from a JSON file (Bronze).
 * First we validate the JSON file against a schema, then reads it into a raw structure.
 * @synthetic
 */
public final class BronzeToSilver {
	private final NotebookConfig notebookConfig;

	private BronzeToSilver(NotebookConfig notebookConfig) {
		this.notebookConfig = notebookConfig;
	}

	public static BronzeToSilver from(NotebookConfig notebookConfig) {
		return new BronzeToSilver(notebookConfig);
	}

	public SilverToGold toSilver() throws Exception {
		//- STEP 1
		validate(notebookConfig);

		//- STEP 2
		final RawNotebook rawNotebook = read(notebookConfig);
		return new SilverToGold(rawNotebook);
	}

	private static void validate(NotebookConfig notebook) throws Exception {
		for (File file : notebook.libraries()) {
			validateLibrary(file);
		}
		//---
		for (File file : notebook.modules()) {
			validateModule(file);
		}
	}

	private static RawNotebook read(NotebookConfig notebookConfig) throws Exception {
		final List<RawLibrary> rawLibrarySources = new ArrayList<>();
		for (File file : notebookConfig.libraries()) {
			rawLibrarySources.add(readLibrary(file));
		}
		//---
		final List<RawModule> rawModuleSources = new ArrayList<>();
		for (File file : notebookConfig.modules()) {
			rawModuleSources.add(readModule(file));
		}
		return new RawNotebook(rawLibrarySources, rawModuleSources);
	}

	private static void validateModule(File file) throws Exception {
		final File schemaFile = new File("C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\main\\java\\io\\vertigo\\vortex\\silver\\module\\raw-module-schema.json");
		JsonValidator.validate(schemaFile, file);
	}

	private static void validateLibrary(File file) throws Exception {
		final File schemaFile = new File("C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\main\\java\\io\\vertigo\\vortex\\silver\\library\\raw-library-schema.json");
		JsonValidator.validate(schemaFile, file);
	}

	private static RawModule readModule(File file) throws IOException {
		Assertion.check().isNotNull(file);
		//---
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(file, RawModule.class);
	}

	private static RawLibrary readLibrary(File file) throws IOException {
		Assertion.check().isNotNull(file);
		//---
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(file, RawLibrary.class);
	}
}
