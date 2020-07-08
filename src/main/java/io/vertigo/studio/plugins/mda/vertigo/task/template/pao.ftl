<#import "macro_ao.ftl" as lib>
package ${pao.packageName};

import javax.inject.Inject;

<#if pao.options >
import java.util.Optional;
</#if>
import io.vertigo.core.node.Home;
import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Generated;
import io.vertigo.datamodel.task.TaskManager;
import io.vertigo.datamodel.task.metamodel.TaskDefinition;
import io.vertigo.datamodel.task.model.Task;
import io.vertigo.datamodel.task.model.TaskBuilder;
import io.vertigo.datastore.impl.dao.StoreServices;

/**
 * This class is automatically generated.
 * DO NOT EDIT THIS FILE DIRECTLY.
 */
 @Generated
public final class ${pao.classSimpleName} implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public ${pao.classSimpleName}(final TaskManager taskManager) {
		Assertion.check().isNotNull(taskManager);
		//-----
		this.taskManager = taskManager;
	}
	<@lib.generateBody pao.taskDefinitions/> 
	private TaskManager getTaskManager() {
		return taskManager;
	}
}
