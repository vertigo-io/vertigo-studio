package io.vertigo.vortex;

import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.VX;
import io.vertigo.vortex.gold.library.types.VXDomainType;
import io.vertigo.vortex.gold.module.VXElementType;

public final class VXDomainTypes {
	private VXDomainTypes() {
	}

	private static final VXKey libraryCoreKey = new VXKey(null, VXElementType.LIBRARY, "core");

	public static final VXDomainType DO_EMAIL = VX.string(libraryCoreKey, "do-email").email().build();
	public static final VXDomainType DO_PHONE = VX.string(libraryCoreKey, "do-phone").pattern("^[0-9]{10}$").build();
	public static final VXDomainType DO_CODE_POSTAL = VX.string(libraryCoreKey, "do-code-postal").pattern("^[0-9]{5}$").build();
	public static final VXDomainType DO_YEAR = VX.integer(libraryCoreKey, "do-year").min(1900).max(2100).build();
	public static final VXDomainType DO_SIRET = VX.string(libraryCoreKey, "do-siret").pattern("^[0-9]{14}$").build();
	public static final VXDomainType DO_IBAN = VX.string(libraryCoreKey, "do-iban").pattern("^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$").build();
	public static final VXDomainType DO_URL = VX.string(libraryCoreKey, "do-url").pattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").build();
	public static final VXDomainType DO_UUID = VX.string(libraryCoreKey, "do-uuid").pattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fAF]{12}$").build();
	public static final VXDomainType DO_NAME = VX.string(libraryCoreKey, "do-name").minLength(2).maxLength(50).build();
	public static final VXDomainType DO_LABEL = VX.string(libraryCoreKey, "do-label").minLength(2).maxLength(100).build();
}
