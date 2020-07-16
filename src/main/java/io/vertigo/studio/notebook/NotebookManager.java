package io.vertigo.studio.notebook;

import io.vertigo.core.node.component.Manager;

public interface NotebookManager extends Manager {

	/**
	 * Renders a notebook as json, so it can be shared, compared, stored and more !
	 * @param notebook the notebook
	 * @return notebook as json
	 */
	String toJson(Notebook notebook);

}
