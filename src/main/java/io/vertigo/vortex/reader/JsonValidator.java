package io.vertigo.vortex.reader;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import io.vertigo.core.lang.Assertion;

final class JsonValidator {

	public static void validate(final File schemaFile, final File dataFile) throws Exception {
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
