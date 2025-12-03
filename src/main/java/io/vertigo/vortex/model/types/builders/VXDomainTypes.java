package io.vertigo.vortex.model.types.builders;

import io.vertigo.vortex.model.types.VX;
import io.vertigo.vortex.model.types.VXDomainType;

public final class VXDomainTypes {
	private VXDomainTypes() {
	}

	public static final VXDomainType DO_EMAIL = VX.string("do-email").email().build();
	public static final VXDomainType DO_PHONE = VX.string("do-phone").pattern("^[0-9]{10}$").build();
	public static final VXDomainType DO_CODE_POSTAL = VX.string("do-code-postal").pattern("^[0-9]{5}$").build();
	public static final VXDomainType DO_YEAR = VX.integer("do-year").min(1900).max(2100).build();
	public static final VXDomainType DO_SIRET = VX.string("do-siret").pattern("^[0-9]{14}$").build();
	public static final VXDomainType DO_IBAN = VX.string("do-iban").pattern("^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$").build();
	public static final VXDomainType DO_URL = VX.string("do-url").pattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").build();
	public static final VXDomainType DO_UUID = VX.string("do-uuid").pattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fAF]{12}$").build();
	public static final VXDomainType DO_NAME = VX.string("do-name").minLength(2).maxLength(50).build();
	public static final VXDomainType DO_LABEL = VX.string("do-label").minLength(2).maxLength(100).build();
}
