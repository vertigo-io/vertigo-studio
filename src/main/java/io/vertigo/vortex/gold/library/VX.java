package io.vertigo.vortex.gold.library;

import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.builders.VXBigDecimalBuilder;
import io.vertigo.vortex.gold.library.builders.VXBooleanBuilder;
import io.vertigo.vortex.gold.library.builders.VXInstantBuilder;
import io.vertigo.vortex.gold.library.builders.VXJsonBuilder;
import io.vertigo.vortex.gold.library.builders.VXLocalDateBuilder;
import io.vertigo.vortex.gold.library.builders.VXNumberBuilder;
import io.vertigo.vortex.gold.library.builders.VXStringBuilder;
import io.vertigo.vortex.gold.library.types.VXDataType;

public final class VX {
	private VX() {
		// private constructor
	}

	public static VXStringBuilder string(final VXKey libraryKey, final String name) {
		return new VXStringBuilder(libraryKey, name);
	}

	public static VXStringBuilder text(final VXKey libraryKey, final String name) {
		return new VXStringBuilder(libraryKey, name);
	}

	public static VXNumberBuilder integer(final VXKey libraryKey, final String name) {
		return new VXNumberBuilder(libraryKey, name, VXDataType.Integer);
	}

	public static VXNumberBuilder long_(final VXKey libraryKey, final String name) {
		return new VXNumberBuilder(libraryKey, name, VXDataType.Long);
	}

	public static VXNumberBuilder double_(final VXKey libraryKey, final String name) {
		return new VXNumberBuilder(libraryKey, name, VXDataType.Double);
	}

	public static VXBigDecimalBuilder bigDecimal(final VXKey libraryKey, final String name) {
		return new VXBigDecimalBuilder(libraryKey, name);
	}

	public static VXBooleanBuilder bool(final VXKey libraryKey, final String name) {
		return new VXBooleanBuilder(libraryKey, name);
	}

	public static VXLocalDateBuilder localDate(final VXKey libraryKey, final String name) {
		return new VXLocalDateBuilder(libraryKey, name);
	}

	public static VXInstantBuilder instant(final VXKey libraryKey, final String name) {
		return new VXInstantBuilder(libraryKey, name);
	}

	public static VXJsonBuilder json(final VXKey libraryKey, final String name) {
		return new VXJsonBuilder(libraryKey, name);
	}
}
