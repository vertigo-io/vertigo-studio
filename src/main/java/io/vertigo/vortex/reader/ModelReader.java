package io.vertigo.vortex.reader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.model.VXModel;
import io.vertigo.vortex.raw.RawModel;


/**
 * Reads a model from a JSON file.
 * The reader first validates the JSON file against a schema, then reads it into a raw model,
 * and finally transforms the raw model into a VXModel.
 * @synthetic
 */
public final class ModelReader {
	private final File file;

	public ModelReader(final File file) {
		Assertion.check().isNotNull(file);
		//---
		this.file = file;
	}

	/**
	 * Processes the JSON file and returns the model.
	 * @return the processed model
	 * @throws Exception if an error occurs during validation, reading, or transformation
	 */
	public VXModel process() throws Exception {
		//- STEP 1
		validate();

		//- STEP 2
		final RawModel rawModel = read();

		//- STEP 3
		return new RawToModel(rawModel).transform();
	}

	private void validate() throws Exception {
		final File schemaFile = new File("C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\main\\java\\io\\vertigo\\vortex\\raw\\raw-schema.json");
		JsonValidator.validate(schemaFile, file);
	}

	private RawModel read() throws IOException {
		Assertion.check().isNotNull(file);
		//---
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(file, RawModel.class);
	}
}
