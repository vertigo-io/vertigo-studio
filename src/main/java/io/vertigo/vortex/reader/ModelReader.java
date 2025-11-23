package io.vertigo.vortex.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.model.VXAttribute;
import io.vertigo.vortex.model.VXCardinality;
import io.vertigo.vortex.model.VXDataType;
import io.vertigo.vortex.model.VXDomainType;
import io.vertigo.vortex.model.VXEntity;
import io.vertigo.vortex.model.VXHeader;
import io.vertigo.vortex.model.VXModel;
import io.vertigo.vortex.model.VXRole;
import io.vertigo.vortex.raw.RawAttribute;
import io.vertigo.vortex.raw.RawDomainType;
import io.vertigo.vortex.raw.RawEntity;
import io.vertigo.vortex.raw.RawModel;

public final class ModelReader {
	private final Map<String, VXDomainType> domainTypeCatalog = new HashMap<>();
	private final Map<String, VXEntity> entityCatalog = new HashMap<>();

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
		return transform(rawModel);
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

	private VXModel transform(final RawModel rawModel) {
		Assertion.check().isNotNull(rawModel);
		//---
		final var header = new VXHeader(rawModel.header().description(), rawModel.header().tags());

		for (final RawDomainType rawDomainType : rawModel.domainTypes()) {
			final var domainType = transform(rawDomainType);
			domainTypeCatalog.put(domainType.name(), domainType);
		}
		for (final RawEntity rawEntity : rawModel.entities()) {
			final var name = "do-" + rawEntity.name();
			domainTypeCatalog.put(name, new VXDomainType(name, VXDataType.Entity, List.of()));
		}

		for (final RawEntity rawEntity : rawModel.entities()) {
			final var entity = transform(rawEntity);
			entityCatalog.put(entity.name(), entity);
		}

		return new VXModel(
				header,
				new ArrayList<>(domainTypeCatalog.values()),
				new ArrayList<>(entityCatalog.values()));
	}

	private VXAttribute transform(final RawAttribute rawAttribute) {
		//default values
		final VXRole role = rawAttribute.role() == null
				? VXRole.DATA
				: VXRole.valueOf(rawAttribute.role().toUpperCase());

		return new VXAttribute(
				rawAttribute.name(),
				domainTypeCatalog.get(rawAttribute.domainType()),
				role,
				VXCardinality.fromSymbol(rawAttribute.cardinality()));
	}

	private VXEntity transform(final RawEntity rawEntity) {
		final List<VXAttribute> attributes = rawEntity.attributes()
				.stream()
				.map(rawAttribute -> transform(rawAttribute))
				.collect(Collectors.toList());

		return new VXEntity(rawEntity.name(), attributes);
	}

	private static VXDomainType transform(final RawDomainType rawDomainType) {
		var dataType = VXDataType.valueOf(rawDomainType.dataType());
		return new VXDomainType(rawDomainType.name(), dataType, List.of());
	}
}
