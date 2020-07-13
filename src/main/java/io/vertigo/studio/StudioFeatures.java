/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2019, Vertigo.io, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.studio;

import io.vertigo.core.node.config.Feature;
import io.vertigo.core.node.config.Features;
import io.vertigo.core.param.Param;
import io.vertigo.studio.impl.mda.MdaManagerImpl;
import io.vertigo.studio.impl.source.NotebookSourceManagerImpl;
import io.vertigo.studio.mda.MdaManager;
import io.vertigo.studio.plugins.mda.vertigo.authorization.AuthorizationGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.domain.java.DomainGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.domain.js.JSGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.domain.sql.SqlGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.domain.ts.TSGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.file.FileInfoGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.search.SearchGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.task.TaskGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.task.test.TaskTestGeneratorPlugin;
import io.vertigo.studio.plugins.mda.vertigo.webservice.WsTsGeneratorPlugin;
import io.vertigo.studio.plugins.source.vertigo.AccountJsonSecuritySourceReaderPlugin;
import io.vertigo.studio.plugins.source.vertigo.JsonStaticMasterDataSourceReaderPlugin;
import io.vertigo.studio.plugins.source.vertigo.StudioSourceReaderPlugin;
import io.vertigo.studio.plugins.source.vertigo.VegaWebServicesSourceReaderPlugin;
import io.vertigo.studio.source.NotebookSourceManager;

public final class StudioFeatures extends Features<StudioFeatures> {

	public StudioFeatures() {
		super("vertigo-studio");
	}

	@Feature("metamodel")
	public StudioFeatures withMetamodel() {
		getModuleConfigBuilder()
				.addComponent(NotebookSourceManager.class, NotebookSourceManagerImpl.class);
		return this;
	}

	@Feature("metamodel.vertigo")
	public StudioFeatures withVertigoMetamodel(final Param... params) {
		getModuleConfigBuilder()
				.addPlugin(StudioSourceReaderPlugin.class, params)
				.addPlugin(AccountJsonSecuritySourceReaderPlugin.class)
				.addPlugin(VegaWebServicesSourceReaderPlugin.class)
				.addPlugin(JsonStaticMasterDataSourceReaderPlugin.class);
		return this;
	}

	@Feature("mda")
	public StudioFeatures withMda() {
		getModuleConfigBuilder()
				.addComponent(MdaManager.class, MdaManagerImpl.class);
		return this;
	}

	@Feature("mda.vertigo")
	public StudioFeatures withVertigoMda() {
		getModuleConfigBuilder()
				.addPlugin(DomainGeneratorPlugin.class)
				.addPlugin(JSGeneratorPlugin.class)
				.addPlugin(TSGeneratorPlugin.class)
				.addPlugin(SqlGeneratorPlugin.class)
				.addPlugin(AuthorizationGeneratorPlugin.class)
				.addPlugin(FileInfoGeneratorPlugin.class)
				.addPlugin(TaskGeneratorPlugin.class)
				.addPlugin(SearchGeneratorPlugin.class)
				.addPlugin(TaskTestGeneratorPlugin.class)
				.addPlugin(WsTsGeneratorPlugin.class);
		return this;
	}

	@Override
	protected void buildFeatures() {
		// nothing by default
	}

}
