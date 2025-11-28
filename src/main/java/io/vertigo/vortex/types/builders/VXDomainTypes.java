package io.vertigo.vortex.types.builders;

import io.vertigo.vortex.types.VX;
import io.vertigo.vortex.types.VXDomainType;

public final class VXDomainTypes {
    private VXDomainTypes() {}

    public static final VXDomainType DO_EMAIL = VX.string("Email").email().build();
    public static final VXDomainType PHONE = VX.string("Phone").pattern("^[0-9]{10}$").build();
    public static final VXDomainType CODE_POSTAL = VX.string("CodePostal").pattern("^[0-9]{5}$").build();
    public static final VXDomainType YEAR = VX.integer("Year").min(1900).max(2100).build();
    public static final VXDomainType SIRET = VX.string("Siret").pattern("^[0-9]{14}$").build();
    public static final VXDomainType IBAN = VX.string("Iban").pattern("^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$").build();
    public static final VXDomainType URL = VX.string("Url").pattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").build();
    public static final VXDomainType UUID = VX.string("Uuid").pattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fAF]{12}$").build();
    public static final VXDomainType NAME = VX.string("Name").minLength(2).maxLength(50).build();
    public static final VXDomainType LABEL = VX.string("Label").minLength(2).maxLength(100).build();
}
