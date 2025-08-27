package io.vertigo.shell.commands.db.model.store;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.commands.db.model.DbModel;
import io.vertigo.shell.commands.db.model.DbModel.JdbcColumn;
import io.vertigo.shell.commands.db.model.DbModel.JdbcConstraint;
import io.vertigo.shell.commands.db.model.DbModel.JdbcFunction;
import io.vertigo.shell.commands.db.model.DbModel.JdbcIndex;
import io.vertigo.shell.commands.db.model.DbModel.JdbcMaterializedView;
import io.vertigo.shell.commands.db.model.DbModel.JdbcPrivilege;
import io.vertigo.shell.commands.db.model.DbModel.JdbcRelation;
import io.vertigo.shell.commands.db.model.DbModel.JdbcSchema;
import io.vertigo.shell.commands.db.model.DbModel.JdbcSequence;
import io.vertigo.shell.commands.db.model.DbModel.JdbcTable;
import io.vertigo.shell.commands.db.model.DbModel.JdbcTrigger;

// Classe pour charger le modèle à partir d’une connexion JDBC
final class DbModelLoader {
	private final Connection connection;

	DbModelLoader(final Connection connection) {
		Assertion.check().isNotNull(connection);
		//---
		this.connection = connection;
	}

	// Charge tous les schémas
	DbModel loadModel() throws SQLException {
		return new DbModel(loadSchemas());
	}

	private List<JdbcSchema> loadSchemas() throws SQLException {
		final List<JdbcSchema> schemas = new ArrayList<>();
		final DatabaseMetaData metaData = connection.getMetaData();

		try (ResultSet rs = metaData.getSchemas()) {
			while (rs.next()) {
				final String schemaName = rs.getString("TABLE_SCHEM");
				final List<JdbcTable> tables = loadTables(schemaName);
				final List<JdbcSequence> sequences = loadSequences(schemaName);
				final List<JdbcMaterializedView> materializedViews = loadMaterializedViews(schemaName);
				final List<JdbcFunction> functions = loadFunctions(schemaName);
				final List<JdbcPrivilege> privileges = loadPrivileges(schemaName);
				schemas.add(new JdbcSchema(schemaName, tables, sequences, materializedViews, functions, privileges));
			}
		}
		return schemas;
	}

	// Charge les tables d’un schéma
	private List<JdbcTable> loadTables(final String schema) throws SQLException {
		final List<JdbcTable> tables = new ArrayList<>();
		final DatabaseMetaData metaData = connection.getMetaData();

		try (ResultSet rs = metaData.getTables(null, schema, "%", new String[] { "TABLE", "VIEW" })) {
			while (rs.next()) {
				final String tableName = rs.getString("TABLE_NAME");
				final String tableType = rs.getString("TABLE_TYPE");
				List<JdbcColumn> columns = loadColumns(metaData, tableName, schema);
				columns = loadPrimaryKeys(metaData, tableName, schema, columns);
				final List<JdbcIndex> indexes = loadIndexes(metaData, tableName, schema);
				final List<JdbcRelation> relations = loadRelations(metaData, tableName, schema);
				final List<JdbcTrigger> triggers = loadTriggers(tableName, schema);
				final List<JdbcConstraint> constraints = loadConstraints(tableName, schema);
				final String comment = loadTableComment(tableName, schema);
				//				JdbcStatistics statistics = loadStatistics(tableName, schema);

				final JdbcTable table = new JdbcTable(tableName, tableType, columns, indexes, relations, triggers, constraints, comment);
				tables.add(table);
			}
		}
		return tables;
	}

	// Charge les colonnes
	private List<JdbcColumn> loadColumns(final DatabaseMetaData metaData, final String tableName, final String schema) throws SQLException {
		final List<JdbcColumn> columns = new ArrayList<>();
		try (ResultSet rs = metaData.getColumns(null, schema, tableName, "%")) {
			while (rs.next()) {
				final String columnName = rs.getString("COLUMN_NAME");
				final String typeName = rs.getString("TYPE_NAME");
				final int columnSize = rs.getInt("COLUMN_SIZE");
				final boolean nullable = "YES".equalsIgnoreCase(rs.getString("IS_NULLABLE"));
				final String defaultValue = rs.getString("COLUMN_DEF");
				final boolean isAutoIncrement = "YES".equalsIgnoreCase(rs.getString("IS_AUTOINCREMENT"));
				final String comment = 1 == 7 ? loadColumnComment(tableName, columnName, schema) : "";
				columns.add(new JdbcColumn(columnName, typeName, columnSize, nullable, false, defaultValue, isAutoIncrement, comment));
			}
		}
		return columns;
	}

	// Charge les clés primaires
	private List<JdbcColumn> loadPrimaryKeys(final DatabaseMetaData metaData, final String tableName, final String schema, final List<JdbcColumn> columns) throws SQLException {
		final List<JdbcColumn> updatedColumns = new ArrayList<>();
		final List<String> primaryKeyColumns = new ArrayList<>();
		try (ResultSet rs = metaData.getPrimaryKeys(null, schema, tableName)) {
			while (rs.next()) {
				primaryKeyColumns.add(rs.getString("COLUMN_NAME"));
			}
		}
		for (final JdbcColumn column : columns) {
			final boolean isPrimaryKey = primaryKeyColumns.contains(column.name());
			updatedColumns.add(new JdbcColumn(
					column.name(), column.typeName(), column.size(), column.nullable(), isPrimaryKey,
					column.defaultValue(), column.isAutoIncrement(), column.comment()));
		}
		return updatedColumns;
	}

	private List<JdbcIndex> loadIndexes(final DatabaseMetaData metaData, final String tableName, final String schema) throws SQLException {
		final Map<String, List<String>> indexColumnsMap = new HashMap<>();
		final Map<String, Boolean> indexUniquenessMap = new HashMap<>();

		try (ResultSet rs = metaData.getIndexInfo(null, schema, tableName, false, false)) {
			while (rs.next()) {
				final String indexName = rs.getString("INDEX_NAME");
				if (indexName == null) {
					continue;
				}
				final boolean nonUnique = rs.getBoolean("NON_UNIQUE");
				final String columnName = rs.getString("COLUMN_NAME");

				indexColumnsMap.computeIfAbsent(indexName, k -> new ArrayList<>()).add(columnName);
				indexUniquenessMap.putIfAbsent(indexName, !nonUnique);
			}
		}

		final List<JdbcIndex> indexes = new ArrayList<>();
		for (final String indexName : indexColumnsMap.keySet()) {
			indexes.add(new JdbcIndex(indexName, indexUniquenessMap.get(indexName), indexColumnsMap.get(indexName)));
		}
		return indexes;
	}

	// Charge les relations
	private List<JdbcRelation> loadRelations(final DatabaseMetaData metaData, final String tableName, final String schema) throws SQLException {
		final List<JdbcRelation> relations = new ArrayList<>();

		try (ResultSet rs = metaData.getImportedKeys(null, schema, tableName)) {
			while (rs.next()) {
				final String fkName = rs.getString("FK_NAME");
				final String fkTable = rs.getString("FKTABLE_NAME");
				final String fkColumn = rs.getString("FKCOLUMN_NAME");
				final String pkTable = rs.getString("PKTABLE_NAME");
				final String pkColumn = rs.getString("PKCOLUMN_NAME");
				relations.add(new JdbcRelation(fkName, fkTable, fkColumn, pkTable, pkColumn));
			}
		}
		return relations;
	}

	// Charge les triggers (PostgreSQL)
	private List<JdbcTrigger> loadTriggers(final String tableName, final String schema) throws SQLException {
		final List<JdbcTrigger> triggers = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(
				"""
						SELECT trigger_name, event_manipulation AS event, action_timing AS timing, action_statement AS procedure
						FROM information_schema.triggers
						WHERE trigger_schema = ? AND event_object_table = ?
						""")) {
			stmt.setString(1, schema);
			stmt.setString(2, tableName);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					triggers.add(new JdbcTrigger(
							rs.getString("trigger_name"),
							rs.getString("event"),
							rs.getString("timing"),
							rs.getString("procedure")));
				}
			}
		}
		return triggers;
	}

	// Charge les contraintes (PostgreSQL)
	private List<JdbcConstraint> loadConstraints(final String tableName, final String schema) throws SQLException {
		final List<JdbcConstraint> constraints = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(
				"""
						SELECT conname AS constraint_name, contype AS constraint_type, pg_get_constraintdef(c.oid) AS definition
						FROM pg_constraint c
						JOIN pg_class t ON c.conrelid = t.oid
						WHERE t.relname = ? AND t.relnamespace = (SELECT oid FROM pg_namespace WHERE nspname = ?)
						AND contype IN ('c', 'u')
						""")) {
			stmt.setString(1, tableName);
			stmt.setString(2, schema);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					final String type = switch (rs.getString("constraint_type")) {
						case "c" -> "CHECK";
						case "u" -> "UNIQUE";
						default -> rs.getString("constraint_type");
					};
					constraints.add(new JdbcConstraint(
							rs.getString("constraint_name"),
							type,
							rs.getString("definition")));
				}
			}
		}
		return constraints;
	}

	// Charge les séquences (PostgreSQL)
	private List<JdbcSequence> loadSequences(final String schema) throws SQLException {
		final List<JdbcSequence> sequences = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(
				"""
						SELECT sequence_name, start_value, increment, minimum_value, maximum_value
						FROM information_schema.sequences
						WHERE sequence_schema = ?
						""")) {
			stmt.setString(1, schema);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					sequences.add(new JdbcSequence(
							rs.getString("sequence_name"),
							rs.getLong("start_value"),
							rs.getLong("increment"),
							rs.getLong("minimum_value"),
							rs.getLong("maximum_value")));
				}
			}
		}
		return sequences;
	}

	// Charge les vues matérialisées (PostgreSQL)
	private List<JdbcMaterializedView> loadMaterializedViews(final String schema) throws SQLException {
		final List<JdbcMaterializedView> materializedViews = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(
				"""
						SELECT matviewname, definition
						FROM pg_matviews
						WHERE schemaname = ?
						""")) {
			stmt.setString(1, schema);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					final String viewName = rs.getString("matviewname");
					final String definition = rs.getString("definition");
					final List<JdbcColumn> columns = loadColumns(connection.getMetaData(), viewName, schema);
					materializedViews.add(new JdbcMaterializedView(viewName, definition, columns));
				}
			}
		}
		return materializedViews;
	}

	// Charge les fonctions (PostgreSQL)
	private List<JdbcFunction> loadFunctions(final String schema) throws SQLException {
		final List<JdbcFunction> functions = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(
				"""
						SELECT routine_name, routine_type, data_type, routine_definition
						FROM information_schema.routines
						WHERE routine_schema = ?
						""")) {
			stmt.setString(1, schema);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					functions.add(new JdbcFunction(
							rs.getString("routine_name"),
							rs.getString("data_type"),
							rs.getString("routine_definition")));
				}
			}
		}
		return functions;
	}

	// Charge les privilèges (PostgreSQL)
	private List<JdbcPrivilege> loadPrivileges(final String schema) throws SQLException {
		final List<JdbcPrivilege> privileges = new ArrayList<>();
		try (PreparedStatement stmt = connection.prepareStatement(
				"""
						SELECT p.table_name, t.table_type, p.grantee, p.privilege_type
						FROM information_schema.table_privileges p
						JOIN information_schema.tables t
						ON p.table_schema = t.table_schema AND p.table_name = t.table_name
						WHERE p.table_schema = ?
								""")) {
			stmt.setString(1, schema);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					privileges.add(new JdbcPrivilege(
							rs.getString("table_name"),
							rs.getString("table_type"),
							rs.getString("grantee"),
							rs.getString("privilege_type")));
				}
			}
		}
		return privileges;
	}

	// Charge le commentaire de la table (PostgreSQL)
	private String loadTableComment(final String tableName, final String schema) throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement(
				"""
						SELECT obj_description((SELECT oid FROM pg_class WHERE relname = ? AND relnamespace = (SELECT oid FROM pg_namespace WHERE nspname = ?)), 'pg_class')
						""")) {
			stmt.setString(1, tableName);
			stmt.setString(2, schema);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() ? rs.getString(1) : null;
			}
		}
	}

	// Charge le commentaire de la colonne (PostgreSQL)
	private String loadColumnComment(final String tableName, final String columnName, final String schema) throws SQLException {
		try (PreparedStatement stmt = connection.prepareStatement(
				"""
						SELECT col_description((SELECT oid FROM pg_class WHERE relname = ? AND relnamespace = (SELECT oid FROM pg_namespace WHERE nspname = ?)),
						                       (SELECT attnum FROM pg_attribute WHERE attrelid = (SELECT oid FROM pg_class WHERE relname = ?) AND attname = ?))
						                       """)) {
			stmt.setString(1, tableName);
			stmt.setString(2, schema);
			stmt.setString(3, tableName);
			stmt.setString(4, columnName);
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next() ? rs.getString(1) : null;
			}
		}
	}

	//	// Charge les statistiques (PostgreSQL)
	//	private JdbcStatistics loadStatistics(String tableName, String schema) throws SQLException {
	//		try (PreparedStatement stmt = connection.prepareStatement(
	//				"""
	//						SELECT n_live_tup AS row_count, seq_scan, idx_scan
	//						FROM pg_stat_user_tables
	//						WHERE schemaname = ? AND relname = ?""")) {
	//			stmt.setString(1, schema);
	//			stmt.setString(2, tableName);
	//			try (ResultSet rs = stmt.executeQuery()) {
	//				if (rs.next()) {
	//					return new JdbcStatistics(
	//							tableName,
	//							rs.getLong("row_count"),
	//							rs.getLong("seq_scan"),
	//							rs.getLong("idx_scan"));
	//				}
	//			}
	//		}
	//		return null;
	//	}
}
