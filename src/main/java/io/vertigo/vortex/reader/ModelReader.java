package io.vertigo.vortex.reader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.model.VXModel;
import io.vertigo.vortex.raw.RawModel;

public final class ModelReader {
	private final File file;

	public ModelReader(final File file) {
		Assertion.check().isNotNull(file);
		//---
		this.file = file;
	}

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
