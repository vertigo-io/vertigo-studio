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
	private Map<String, VXDomainType> domainTypeCatalog = new HashMap<>();
	private Map<String, VXEntity> entityCatalog = new HashMap<>();

	private final File file;

	public ModelReader(File file) {
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
		File schemaFile = new File("C:\\Users\\pchretien\\GitHub\\vertigo-studio\\src\\main\\java\\io\\vertigo\\vortex\\raw\\raw-schema.json");
		JsonValidator.validate(schemaFile, file);
	}

	private RawModel read() throws IOException {
		Assertion.check().isNotNull(file);
		//---
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(file, RawModel.class);
	}

	private VXModel transform(RawModel rawModel) {
		Assertion.check().isNotNull(rawModel);
		//---
		var header = new VXHeader(rawModel.rawHeader.description, rawModel.rawHeader.tags);

		for (RawDomainType rawDomainType : rawModel.rawDomainTypes) {
			var domainType = transform(rawDomainType);
			domainTypeCatalog.put(domainType.name, domainType);
		}
		for (RawEntity rawEntity : rawModel.rawEntities) {
			var name = "do-" + rawEntity.name;
			domainTypeCatalog.put(name, new VXDomainType(name, "entity"));
		}

		for (RawEntity rawEntity : rawModel.rawEntities) {
			var entity = transform(rawEntity);
			entityCatalog.put(entity.name, entity);
		}

		return new VXModel(
				header,
				new ArrayList<>(domainTypeCatalog.values()),
				new ArrayList<>(entityCatalog.values()));
	}

	private VXAttribute transform(RawAttribute rawAttribute) {
		//default values
		VXRole role = rawAttribute.role == null
				? VXRole.DATA
				: VXRole.valueOf(rawAttribute.role.toUpperCase());

		return new VXAttribute(
				rawAttribute.name,
				domainTypeCatalog.get(rawAttribute.domainType),
				role,
				VXCardinality.fromSymbol(rawAttribute.cardinality));
	}

	private VXEntity transform(RawEntity rawEntity) {
		List<VXAttribute> attributes = rawEntity.rawAttributes
				.stream()
				.map(rawAttribute -> transform(rawAttribute))
				.collect(Collectors.toList());

		return new VXEntity(rawEntity.name, attributes);
	}

	private static VXDomainType transform(RawDomainType rawDomainType) {
		return new VXDomainType(rawDomainType.name, rawDomainType.dataType);
	}
}
