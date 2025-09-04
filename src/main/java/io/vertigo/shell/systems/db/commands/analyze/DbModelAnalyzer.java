package io.vertigo.shell.systems.db.commands.analyze;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertigo.shell.systems.db.DbModel;
import io.vertigo.shell.systems.db.DbModel.JdbcTable;

final class DbModelAnalyzer {

	static DbModelAnalysisReport analyze(final DbModel model) {
		final List<String> tablesWithoutPrimaryKey = new ArrayList<>();
		final List<String> nonNullableColumnsWithoutDefault = new ArrayList<>();
		final List<String> invalidRelations = new ArrayList<>();
		final List<String> redundantIndexes = new ArrayList<>();
		final List<String> trivialCheckConstraints = new ArrayList<>();
		final List<String> undocumentedObjects = new ArrayList<>();

		final Map<String, JdbcTable> tableMap = new HashMap<>();
		final Map<String, Set<String>> directFanIn = new HashMap<>();
		final Map<String, Set<String>> directFanOut = new HashMap<>();

		// Préparer les noms complets de tables
		for (final DbModel.JdbcSchema schema : model.schemas()) {
			for (final JdbcTable table : schema.tables()) {
				final String fullName = schema.name() + "." + table.name();
				tableMap.put(fullName, table);
				directFanIn.put(fullName, new HashSet<>());
				directFanOut.put(fullName, new HashSet<>());
			}
		}

		// Analyse structurelle + dépendances
		for (final DbModel.JdbcSchema schema : model.schemas()) {
			for (final JdbcTable table : schema.tables()) {
				final String tableName = schema.name() + "." + table.name();

				// Clé primaire
				if (table.columns().stream().noneMatch(DbModel.JdbcColumn::isPrimaryKey)) {
					tablesWithoutPrimaryKey.add(tableName);
				}

				for (final DbModel.JdbcColumn column : table.columns()) {
					if (!column.nullable() && column.defaultValue() == null) {
						nonNullableColumnsWithoutDefault.add(tableName + "." + column.name());
					}
					if (column.comment() == null || column.comment().isBlank()) {
						undocumentedObjects.add("Column: " + tableName + "." + column.name());
					}
				}

				// Relations
				for (final DbModel.JdbcRelation relation : table.relations()) {
					final String targetTable = schema.name() + "." + relation.targetTable();
					directFanOut.get(tableName).add(targetTable);
					directFanIn.computeIfAbsent(targetTable, k -> new HashSet<>()).add(tableName);

					final JdbcTable tgt = tableMap.get(targetTable);
					if (tgt == null || tgt.columns().stream().noneMatch(c -> c.name().equals(relation.targetColumn()))) {
						invalidRelations.add("Relation " + relation.name() + " in " + tableName +
								" -> missing target " + relation.targetTable() + "." + relation.targetColumn());
					}
				}

				// Indexes
				final Set<Set<String>> seenIndexes = new HashSet<>();
				for (final DbModel.JdbcIndex index : table.indexes()) {
					final Set<String> cols = new HashSet<>(index.columnNames());
					if (!seenIndexes.add(cols)) {
						redundantIndexes.add(tableName + "." + index.name());
					}
				}

				// Contraintes triviales
				for (final DbModel.JdbcConstraint constraint : table.constraints()) {
					if ("CHECK".equalsIgnoreCase(constraint.type()) &&
							("1=1".equalsIgnoreCase(constraint.definition()) || constraint.definition().isBlank())) {
						trivialCheckConstraints.add(tableName + "." + constraint.name());
					}
				}

				// Commentaire manquant
				if (table.comment() == null || table.comment().isBlank()) {
					undocumentedObjects.add("Table: " + tableName);
				}
			}
		}

		// Fan-in transitif
		final Map<String, Integer> transitiveFanIn = computeTransitiveFanIn(directFanIn);

		// Construction des stats finales
		final Map<String, DbModelAnalysisReport.TableDependencyStats> dependencyStats = new HashMap<>();
		for (final String tableName : tableMap.keySet()) {
			final int fanIn = directFanIn.getOrDefault(tableName, Set.of()).size();
			final int fanOut = directFanOut.getOrDefault(tableName, Set.of()).size();
			final int fanInTrans = transitiveFanIn.getOrDefault(tableName, 0);
			dependencyStats.put(tableName, new DbModelAnalysisReport.TableDependencyStats(fanIn, fanOut, fanInTrans));
		}

		return new DbModelAnalysisReport(
				tablesWithoutPrimaryKey,
				nonNullableColumnsWithoutDefault,
				invalidRelations,
				redundantIndexes,
				trivialCheckConstraints,
				undocumentedObjects,
				dependencyStats);
	}

	private static Map<String, Integer> computeTransitiveFanIn(final Map<String, Set<String>> reverseGraph) {
		final Map<String, Integer> result = new HashMap<>();
		for (final String target : reverseGraph.keySet()) {
			final Set<String> visited = new HashSet<>();
			final Deque<String> stack = new ArrayDeque<>(reverseGraph.getOrDefault(target, Set.of()));
			while (!stack.isEmpty()) {
				final String source = stack.pop();
				if (visited.add(source)) {
					stack.addAll(reverseGraph.getOrDefault(source, Set.of()));
				}
			}
			result.put(target, visited.size());
		}
		return result;
	}
}
