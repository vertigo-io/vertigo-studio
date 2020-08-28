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
/**
 *
 */
package io.vertigo.studio.gennerator.vertigo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Collections;

import io.vertigo.core.lang.WrappedException;
import io.vertigo.core.node.Node;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.database.sql.SqlManager;
import io.vertigo.database.sql.connection.SqlConnection;
import io.vertigo.database.sql.statement.SqlStatement;

/**
 * SQL Script executor.
 * @author npiedeloup
 */
public final class DataBaseScriptUtil {

	private DataBaseScriptUtil() {
		//util
	}

	public static void execSqlScript(final SqlConnection connection, final String scriptPath, final ResourceManager resourceManager, final SqlManager sqlManager) {
		try (final BufferedReader in = new BufferedReader(new InputStreamReader(resourceManager.resolve(scriptPath).openStream()))) {
			final StringBuilder crebaseSql = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				final String adaptedInputLine = inputLine.replaceAll("-- .*", "");//removed comments
				if (!"".equals(adaptedInputLine)) {
					crebaseSql.append(adaptedInputLine).append('\n');
				}
				if (inputLine.trim().endsWith(";")) {
					execPreparedStatement(connection, sqlManager, crebaseSql.toString());
					crebaseSql.setLength(0);
				}
			}
		} catch (final IOException | SQLException e) {
			throw WrappedException.wrap(e, "Can't exec script {0}", scriptPath);
		}
	}

	private static void execPreparedStatement(final SqlConnection connection, final SqlManager sqlManager, final String sql) throws SQLException {
		sqlManager.executeUpdate(SqlStatement.builder(sql).build(), Collections.emptyMap(), connection);
	}

	public static void execSqlScript(final String sqlScript, final Node node) {
		final ResourceManager resourceManager = node.getComponentSpace().resolve(ResourceManager.class);
		final SqlManager sqlManager = node.getComponentSpace().resolve(SqlManager.class);

		final SqlConnection connection = sqlManager.getConnectionProvider(SqlManager.MAIN_CONNECTION_PROVIDER_NAME).obtainConnection();
		DataBaseScriptUtil.execSqlScript(connection, sqlScript, resourceManager, sqlManager);
	}
}
