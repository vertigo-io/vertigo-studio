package io.vertigo.vortex.impl.notebook;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.vortex.impl.notebook.raw.RawNotebook;
import io.vertigo.vortex.impl.notebook.raw.library.RawLibrary;
import io.vertigo.vortex.impl.notebook.raw.module.RawModule;
import io.vertigo.vortex.notebook.VXNotebookConfig;

/**
 * Reads a JSON file (Bronze), and transforms it into a rawNotebook (Silver).  
 * First we validate the JSON file against a schema, then reads it into a raw structure.
 * @synthetic
 */
final class FileToRaw {
	private static final File LIBRARY_SCHEMA_FILE = new File("src/main/java/io/vertigo/vortex/impl/notebook/schemas/library-schema.json");
	private static final File MODULE_SCHEMA_FILE = new File("src/main/java/io/vertigo/vortex/impl/notebook/schemas/module-schema.json");

	private final VXNotebookConfig notebookConfig;

	private FileToRaw(VXNotebookConfig notebookConfig) {
		Assertion.check().isNotNull(notebookConfig);
		//---
		this.notebookConfig = notebookConfig;
	}

	static FileToRaw from(VXNotebookConfig notebookConfig) {
		Assertion.check().isNotNull(notebookConfig);
		//---
		return new FileToRaw(notebookConfig);
	}

	RawToNotebook toRaw() throws Exception {
		//- STEP 1 - Schema Validation
		validateNotebook(notebookConfig);

		//- STEP 2 - Transform (bronze) Json file into Raw java classes.
		final RawNotebook rawNotebook = readNotebook(notebookConfig);
		return new RawToNotebook(rawNotebook);
	}

	private static void validateNotebook(VXNotebookConfig notebook) throws Exception {
		for (File file : notebook.libraries()) {
			validateFile(LIBRARY_SCHEMA_FILE, file);
		}
		//---
		for (File file : notebook.modules()) {
			validateFile(MODULE_SCHEMA_FILE, file);
		}
	}

	private static RawNotebook readNotebook(VXNotebookConfig notebookConfig) throws Exception {
		final List<RawLibrary> rawLibraries = notebookConfig.libraries().stream()
				.map(f -> readFile(f, RawLibrary.class))
				.toList();
		//---
		final List<RawModule> rawModules = notebookConfig.modules().stream()
				.map(f -> readFile(f, RawModule.class))
				.toList();

		return new RawNotebook(
				rawLibraries,
				rawModules);
	}

	private static <T> T readFile(File file, Class<T> clazz) {
		Assertion.check()
				.isNotNull(file)
				.isNotNull(clazz);
		//---
		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(file, clazz);
		} catch (Exception e) {
			throw new VSystemException(e, "Unable to read JSON");
		}
	}

	/**
	 * Validates a data file against a schema file.
	 * @param schemaFile the file containing the JSON schema
	 * @param dataFile the file containing the JSON data to validate
	 * @throws Exception if an error occurs during validation
	 */
	private static void validateFile(final File schemaFile, final File dataFile) throws Exception {
		Assertion.check()
				.isNotNull(schemaFile)
				.isNotNull(dataFile);
		//---
		final ObjectMapper mapper = new ObjectMapper();

		// Charge le schéma et le JSON à valider
		final JsonNode schemaNode = mapper.readTree(schemaFile);
		final JsonNode dataNode = mapper.readTree(dataFile);

		// Crée un validateur JSON Schema
		final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
		final JsonSchema schema = factory.getJsonSchema(schemaNode);
		// Valide le document JSON
		final ProcessingReport report = schema.validate(dataNode);
		// Affiche les erreurs éventuelles
		if (report.isSuccess()) {
			System.out.println("✅ JSON valide : " + dataFile.getName());
		} else {
			System.out.println("❌ JSON invalide : " + dataFile.getName());
			System.out.println(report);
		}
	}
}
