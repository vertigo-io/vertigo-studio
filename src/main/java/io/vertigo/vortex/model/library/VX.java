package io.vertigo.vortex.model.library;

import io.vertigo.vortex.model.library.builders.VXBigDecimalBuilder;
import io.vertigo.vortex.model.library.builders.VXBooleanBuilder;
import io.vertigo.vortex.model.library.builders.VXEntityBuilder;
import io.vertigo.vortex.model.library.builders.VXInstantBuilder;
import io.vertigo.vortex.model.library.builders.VXJsonBuilder;
import io.vertigo.vortex.model.library.builders.VXLocalDateBuilder;
import io.vertigo.vortex.model.library.builders.VXNumberBuilder;
import io.vertigo.vortex.model.library.builders.VXStringBuilder;
import io.vertigo.vortex.model.library.types.VXDataType;

public final class VX {
	private VX() {
		// private constructor
	}

	public static VXStringBuilder string(final String name) {
		return new VXStringBuilder(name);
	}

	public static VXStringBuilder text(final String name) {
		return new VXStringBuilder(name);
	}

	public static VXNumberBuilder integer(final String name) {
		return new VXNumberBuilder(name, VXDataType.Integer);
	}

	public static VXNumberBuilder long_(final String name) {
		return new VXNumberBuilder(name, VXDataType.Long);
	}

	public static VXNumberBuilder double_(final String name) {
		return new VXNumberBuilder(name, VXDataType.Double);
	}

	public static VXBigDecimalBuilder bigDecimal(final String name) {
		return new VXBigDecimalBuilder(name);
	}

	public static VXBooleanBuilder bool(final String name) {
		return new VXBooleanBuilder(name);
	}

	public static VXLocalDateBuilder localDate(final String name) {
		return new VXLocalDateBuilder(name);
	}

	public static VXInstantBuilder instant(final String name) {
		return new VXInstantBuilder(name);
	}

	public static VXJsonBuilder json(final String name) {
		return new VXJsonBuilder(name);
	}

	public static VXEntityBuilder entity(final String name) {
		return new VXEntityBuilder(name);
	}

}
