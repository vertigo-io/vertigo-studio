package io.vertigo.vortex.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.bronze.NotebookConfig;
import io.vertigo.vortex.silver.RawNotebook;
import io.vertigo.vortex.silver.library.RawLibrary;
import io.vertigo.vortex.silver.module.RawModule;

/**
 * Reads a JSON file (Bronze) and transforms it to a rawNotebook (Silver).  
 * First we validate the JSON file against a schema, then reads it into a raw structure.
 * @synthetic
 */
public final class Bronze {
	private final NotebookConfig notebookConfig;

	private Bronze(NotebookConfig notebookConfig) {
		Assertion.check().isNotNull(notebookConfig);
		//---
		this.notebookConfig = notebookConfig;
	}

	public static Bronze from(NotebookConfig notebookConfig) {
		return new Bronze(notebookConfig);
	}

	public Silver toSilver() throws Exception {
		//- STEP 1
		validate(notebookConfig);

		//- STEP 2
		final RawNotebook rawNotebook = read(notebookConfig);
		return new Silver(rawNotebook);
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
		validate(schemaFile, file);
	}

	private static void validateLibrary(File file) throws Exception {
		final File schemaFile = new File("C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\main\\java\\io\\vertigo\\vortex\\silver\\library\\raw-library-schema.json");
		validate(schemaFile, file);
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

	/**
	 * Validates a data file against a schema file.
	 * @param schemaFile the file containing the JSON schema
	 * @param dataFile the file containing the JSON data to validate
	 * @throws Exception if an error occurs during validation
	 */
	private static void validate(final File schemaFile, final File dataFile) throws Exception {
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
			System.out.println("✅ JSON valide.");
		} else {
			System.out.println("❌ JSON invalide :");
			System.out.println(report);
		}
	}
}
