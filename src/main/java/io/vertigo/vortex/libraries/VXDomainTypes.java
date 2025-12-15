package io.vertigo.vortex.libraries;

import io.vertigo.vortex.notebook.VXElementType;
import io.vertigo.vortex.notebook.VXKey;
import io.vertigo.vortex.notebook.library.VX;
import io.vertigo.vortex.notebook.library.types.VXDomainType;

public final class VXDomainTypes {
	private VXDomainTypes() {
	}

	private static final VXKey LIBRARY_CORE_KEY = new VXKey(null, VXElementType.LIBRARY, "core");

	public static final VXDomainType DO_EMAIL = VX.string(LIBRARY_CORE_KEY, "do-email").email().build();
	public static final VXDomainType DO_PHONE = VX.string(LIBRARY_CORE_KEY, "do-phone").pattern("^[0-9]{10}$").build();
	public static final VXDomainType DO_CODE_POSTAL = VX.string(LIBRARY_CORE_KEY, "do-code-postal").pattern("^[0-9]{5}$").build();
	public static final VXDomainType DO_YEAR = VX.integer(LIBRARY_CORE_KEY, "do-year").min(1900).max(2100).build();
	public static final VXDomainType DO_SIRET = VX.string(LIBRARY_CORE_KEY, "do-siret").pattern("^[0-9]{14}$").build();
	public static final VXDomainType DO_IBAN = VX.string(LIBRARY_CORE_KEY, "do-iban").pattern("^[A-Z]{2}[0-9]{2}[A-Z0-9]{1,30}$").build();
	public static final VXDomainType DO_URL = VX.string(LIBRARY_CORE_KEY, "do-url").pattern("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]").build();
	public static final VXDomainType DO_UUID = VX.string(LIBRARY_CORE_KEY, "do-uuid").pattern("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fAF]{12}$").build();
	public static final VXDomainType DO_NAME = VX.string(LIBRARY_CORE_KEY, "do-name").minLength(2).maxLength(50).build();
	public static final VXDomainType DO_LABEL = VX.string(LIBRARY_CORE_KEY, "do-label").minLength(2).maxLength(100).build();
}
