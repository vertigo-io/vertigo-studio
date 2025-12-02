package io.vertigo.vortex.model.types;

import io.vertigo.vortex.model.types.builders.VXBooleanBuilder;
import io.vertigo.vortex.model.types.builders.VXEntityBuilder;
import io.vertigo.vortex.model.types.builders.VXJsonBuilder;
import io.vertigo.vortex.model.types.builders.VXNumberBuilder;
import io.vertigo.vortex.model.types.builders.VXStringBuilder;

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

    public static VXBooleanBuilder bool(final String name) {
        return new VXBooleanBuilder(name);
    }

    public static VXJsonBuilder json(final String name) {
        return new VXJsonBuilder(name);
    }

    public static VXEntityBuilder entity(final String name) {
        return new VXEntityBuilder(name);
    }
}

