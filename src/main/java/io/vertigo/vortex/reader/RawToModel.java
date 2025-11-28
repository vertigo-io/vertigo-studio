package io.vertigo.vortex.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;
import io.vertigo.vortex.model.VXAttribute;
import io.vertigo.vortex.model.VXCardinality;
import io.vertigo.vortex.model.VXEntity;
import io.vertigo.vortex.model.VXFile;
import io.vertigo.vortex.model.VXHeader;
import io.vertigo.vortex.model.VXRole;
import io.vertigo.vortex.raw.RawAttribute;
import io.vertigo.vortex.raw.RawDomainType;
import io.vertigo.vortex.raw.RawEntity;
import io.vertigo.vortex.raw.RawFile;
import io.vertigo.vortex.types.VXDataType;
import io.vertigo.vortex.types.VXDomainType;

/**
 * Transforms a raw model into a VXModel.
 * This class is responsible for converting the raw data read from the JSON file
 * into the final, structured VXModel.
 * It builds a catalog of domain types and entities.
 * @synthetic
 */
final class RawToModel {
	private final RawFile rawFile;
	private final Map<String, VXDomainType> domainTypeCatalog = new HashMap<>();
	private final Map<String, VXEntity> entityCatalog = new HashMap<>();

	RawToModel(final RawFile rawFile) {
		Assertion.check().isNotNull(rawFile);
		//---
		this.rawFile = rawFile;
	}

	/**
	 * Transforms the raw model into a VXModel.
	 * @return the transformed VXModel
	 */
	VXFile transform() {
		Assertion.check().isNotNull(rawFile);
		//---
		final var header = new VXHeader(rawFile.header().description(), rawFile.header().tags());

		for (final RawDomainType rawDomainType : rawFile.domainTypes()) {
			final var domainType = transform(rawDomainType);
			domainTypeCatalog.put(domainType.name(), domainType);
		}
		for (final RawEntity rawEntity : rawFile.entities()) {
			final var name = "do-" + rawEntity.name();
			domainTypeCatalog.put(name, new VXDomainType(name, VXDataType.Entity, List.of(), List.of()));
		}

		for (final RawEntity rawEntity : rawFile.entities()) {
			final var entity = transform(rawEntity);
			entityCatalog.put(entity.name(), entity);
		}

		return new VXFile(
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
		return new VXDomainType(rawDomainType.name(), dataType, List.of(), List.of());
	}

}
