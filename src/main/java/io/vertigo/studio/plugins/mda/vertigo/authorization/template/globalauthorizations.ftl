package ${packageName};

import io.vertigo.account.authorization.metamodel.Authorization;
import io.vertigo.account.authorization.metamodel.AuthorizationName;
import io.vertigo.core.node.App;

/**
 * Warning. This class is generated automatically !
 *
 * Enum of the authorizations known by the application.
 */
public enum ${classSimpleName} implements AuthorizationName {

<#list globalauthorizations as authorization>
	/**
	 * ${authorization.comment.orElse(authorization.operationName)}.
	 */
	${authorization.operationName}<#if authorization_has_next>,<#else>;</#if>
</#list>

	/**
	 * Get the associated authorization.
	 *
	 * @param code authorization code
	 * @return authorization
	 */
	public static Authorization of(final String code) {
		return App.getApp().getDefinitionSpace().resolve(code, Authorization.class);
	}

	/**
	 * Get the associated authorization.
	 *
	 * @return role
	 */
	public Authorization getAuthorization() {
		return of(name());
	}

}
