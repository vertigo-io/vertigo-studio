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
import io.vertigo.vortex.model.Attribute;
import io.vertigo.vortex.model.Cardinality;
import io.vertigo.vortex.model.DomainType;
import io.vertigo.vortex.model.Entity;
import io.vertigo.vortex.model.Header;
import io.vertigo.vortex.model.Model;
import io.vertigo.vortex.model.Role;
import io.vertigo.vortex.raw.RawAttribute;
import io.vertigo.vortex.raw.RawDomainType;
import io.vertigo.vortex.raw.RawEntity;
import io.vertigo.vortex.raw.RawModel;

public final class ModelReader {
	private Map<String, DomainType> domainTypeCatalog = new HashMap<>();
	private Map<String, Entity> entityCatalog = new HashMap<>();

	private final File file;

	public ModelReader(File file) {
		Assertion.check().isNotNull(file);
		//---
		this.file = file;
	}

	public Model process() throws Exception {
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

	private Model transform(RawModel rawModel) {
		Assertion.check().isNotNull(rawModel);
		//---
		var header = new Header(rawModel.rawHeader.description, rawModel.rawHeader.tags);

		for (RawDomainType rawDomainType : rawModel.rawDomainTypes) {
			var domainType = transform(rawDomainType);
			domainTypeCatalog.put(domainType.name, domainType);
		}
		for (RawEntity rawEntity : rawModel.rawEntities) {
			var name = "do-" + rawEntity.name;
			domainTypeCatalog.put(name, new DomainType(name, "entity"));
		}

		for (RawEntity rawEntity : rawModel.rawEntities) {
			var entity = transform(rawEntity);
			entityCatalog.put(entity.name, entity);
		}

		return new Model(
				header,
				new ArrayList<>(domainTypeCatalog.values()),
				new ArrayList<>(entityCatalog.values()));
	}

	private Attribute transform(RawAttribute rawAttribute) {
		//default values
		Role role = rawAttribute.role == null
				? Role.DATA
				: Role.valueOf(rawAttribute.role.toUpperCase());

		return new Attribute(
				rawAttribute.name,
				domainTypeCatalog.get(rawAttribute.domainType),
				role,
				Cardinality.fromSymbol(rawAttribute.cardinality));
	}

	private Entity transform(RawEntity rawEntity) {
		List<Attribute> attributes = rawEntity.rawAttributes
				.stream()
				.map(rawAttribute -> transform(rawAttribute))
				.collect(Collectors.toList());

		return new Entity(rawEntity.name, attributes);
	}

	private static DomainType transform(RawDomainType rawDomainType) {
		return new DomainType(rawDomainType.name, rawDomainType.dataType);
	}
}
