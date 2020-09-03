/**
 * vertigo - application development platform
 *
 * Copyright (C) 2013-2020, Vertigo.io, team@vertigo.io
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
import io.vertigo.studio.generator.GeneratorManager;
import io.vertigo.studio.impl.generator.GeneratorManagerImpl;
import io.vertigo.studio.impl.notebook.NotebookManagerImpl;
import io.vertigo.studio.impl.source.SourceManagerImpl;
import io.vertigo.studio.notebook.NotebookManager;
import io.vertigo.studio.plugins.generator.vertigo.authorization.AuthorizationGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.domain.java.DomainGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.domain.js.JSGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.domain.sql.SqlGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.domain.ts.TSGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.file.FileInfoGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.search.SearchGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.task.TaskGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.task.test.TaskTestGeneratorPlugin;
import io.vertigo.studio.plugins.generator.vertigo.webservice.WsTsGeneratorPlugin;
import io.vertigo.studio.plugins.source.vertigo.AccountJsonSecuritySourceReaderPlugin;
import io.vertigo.studio.plugins.source.vertigo.JsonStaticMasterDataSourceReaderPlugin;
import io.vertigo.studio.plugins.source.vertigo.StudioSourceReaderPlugin;
import io.vertigo.studio.plugins.source.vertigo.VegaWebServicesSourceReaderPlugin;
import io.vertigo.studio.source.SourceManager;

public final class StudioFeatures extends Features<StudioFeatures> {

	public StudioFeatures() {
		super("vertigo-studio");
	}

	@Feature("source")
	public StudioFeatures withSource() {
		getModuleConfigBuilder()
				.addComponent(SourceManager.class, SourceManagerImpl.class);
		return this;
	}

	@Feature("source.vertigo")
	public StudioFeatures withVertigoSource(final Param... params) {
		getModuleConfigBuilder()
				.addPlugin(StudioSourceReaderPlugin.class, params)
				.addPlugin(AccountJsonSecuritySourceReaderPlugin.class)
				.addPlugin(VegaWebServicesSourceReaderPlugin.class)
				.addPlugin(JsonStaticMasterDataSourceReaderPlugin.class);
		return this;
	}

	@Feature("generator")
	public StudioFeatures withGenerator() {
		getModuleConfigBuilder()
				.addComponent(GeneratorManager.class, GeneratorManagerImpl.class);
		return this;
	}

	@Feature("generator.vertigo")
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
		getModuleConfigBuilder()
				.addComponent(NotebookManager.class, NotebookManagerImpl.class);
	}

}
