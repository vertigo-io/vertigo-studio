package ${packageName};

<#if securedentities?size != 0 >
import io.vertigo.account.authorization.definitions.Authorization;
</#if>
import io.vertigo.account.authorization.definitions.AuthorizationName;
import io.vertigo.account.authorization.definitions.OperationName;
<#if securedentities?size != 0 >
import io.vertigo.core.node.Node;
</#if>
<#list securedentities as securedEntity>
import ${securedEntity.classCanonicalName};
</#list>

/**
 * Warning. This class is generated automatically !
 *
 * Enum of the security authorizations and operations associated with secured entities known by the application.
 */
public final class ${classSimpleName} {

	private ${classSimpleName}() {
		//private constructor
	}

<#list securedentities as securedEntity>
	/**
	 * Authorizations of ${securedEntity.classSimpleName}.
	 */
	public enum ${securedEntity.classSimpleName}Authorizations implements AuthorizationName {
	<#list securedEntity.operations as operation>
		/** ${operation.comment.orElse(operation.operationName)}. */
		Atz${securedEntity.classSimpleName}$${operation.operationName}<#if operation_has_next>,<#else>;</#if>
	</#list>

		/**
		 * Get the associated authorization.
		 *
		 * @param code authorization code
		 * @return authorization
		 */
		public static Authorization of(final String code) {
			return Node.getNode().getDefinitionSpace().resolve(code, Authorization.class);
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

	/**
	 * Operations of ${securedEntity.classSimpleName}.
	 */
	public enum ${securedEntity.classSimpleName}Operations implements OperationName<${securedEntity.classSimpleName}> {
	<#list securedEntity.operations as operation>
		/** ${operation.comment.orElse(operation.operationName)}. */
		${operation.operationName}<#if operation_has_next>,<#else>;</#if>
	</#list>
	}
</#list>
}
