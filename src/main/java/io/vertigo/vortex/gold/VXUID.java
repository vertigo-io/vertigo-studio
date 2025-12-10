package io.vertigo.vortex.gold;

import io.vertigo.core.lang.Assertion;

public record VXUID(VXUID owner, String type, String name) {

	public VXUID {
		Assertion.check()
				.isNotBlank(type)
				.isTrue("module".equals(type) || "entity".equals(type), "type must be in 'module', 'entity'")
				//if owner == null then type == 'module'
				.isTrue(owner != null || "module".equals(type), "owner must be fulfilled")
				//if owner != null then type != 'module'
				.isTrue(owner == null || !"module".equals(type), "module has no owner")
				//if name == entity then owner.type == 'module'
				.isTrue(!"entity".equals(name) || "module".equals(owner.type), "owner of an entity must be a module")
				.isNotBlank(name);
	}
}
