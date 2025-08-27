package io.vertigo.shell.commands.db.model;

import java.util.List;

public record DbModel(List<JdbcSchema> schemas) {

	public DbModel {
		schemas = List.copyOf(schemas);
	}

	// Représente un schéma dans la base de données
	public record JdbcSchema(
			String name,
			List<JdbcTable> tables,
			List<JdbcSequence> sequences,
			List<JdbcMaterializedView> materializedViews,
			List<JdbcFunction> functions,
			List<JdbcPrivilege> privileges) {
		public JdbcSchema {
			tables = List.copyOf(tables);
			sequences = List.copyOf(sequences);
			materializedViews = List.copyOf(materializedViews);
			functions = List.copyOf(functions);
			privileges = List.copyOf(privileges);
		}
	}

	// Représente une table dans la base de données
	public record JdbcTable(
			String name,
			String type,
			List<JdbcColumn> columns,
			List<JdbcIndex> indexes,
			List<JdbcRelation> relations,
			List<JdbcTrigger> triggers,
			List<JdbcConstraint> constraints,
			String comment) {
		public JdbcTable {
			columns = List.copyOf(columns);
			indexes = List.copyOf(indexes);
			relations = List.copyOf(relations);
			triggers = List.copyOf(triggers);
			constraints = List.copyOf(constraints);
		}
	}

	// Représente une colonne dans une table
	public record JdbcColumn(
			String name,
			String typeName,
			int size,
			boolean nullable,
			boolean isPrimaryKey,
			String defaultValue,
			boolean isAutoIncrement,
			String comment) {
	}

	// Représente un index sur une table
	public record JdbcIndex(
			String name,
			boolean unique,
			List<String> columnNames) {
		public JdbcIndex {
			columnNames = List.copyOf(columnNames);
		}
	}

	// Représente une relation (clé étrangère) entre tables
	public record JdbcRelation(
			String name,
			String sourceTable,
			String sourceColumn,
			String targetTable,
			String targetColumn) {
	}

	// Représente un trigger sur une table
	public record JdbcTrigger(
			String name,
			String event,
			String timing,
			String procedure) {
	}

	// Représente une contrainte (CHECK, UNIQUE)
	public record JdbcConstraint(
			String name,
			String type,
			String definition) {
	}

	// Représente une séquence
	public record JdbcSequence(
			String name,
			long startValue,
			long increment,
			long minValue,
			long maxValue) {
	}

	// Représente une vue matérialisée
	public record JdbcMaterializedView(
			String name,
			String definition,
			List<JdbcColumn> columns) {
		public JdbcMaterializedView {
			columns = List.copyOf(columns);
		}
	}

	// Représente une fonction ou procédure stockée
	public record JdbcFunction(
			String name,
			String returnType,
			String definition) {
	}

	// Représente une permission
	public record JdbcPrivilege(
			String objectName,
			String objectType,
			String grantee,
			String privilege) {
	}
}
