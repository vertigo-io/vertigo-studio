package io.vertigo.shell.labs.Jdbc.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertigo.shell.labs.Jdbc.model.JdbcModel.JdbcTable;

public final class JdbcModelAnalyzer {

	public static JdbcModelAnalysisReport analyze(JdbcModel model) {
		List<String> tablesWithoutPrimaryKey = new ArrayList<>();
		List<String> nonNullableColumnsWithoutDefault = new ArrayList<>();
		List<String> invalidRelations = new ArrayList<>();
		List<String> redundantIndexes = new ArrayList<>();
		List<String> trivialCheckConstraints = new ArrayList<>();
		List<String> undocumentedObjects = new ArrayList<>();

		Map<String, JdbcTable> tableMap = new HashMap<>();
		Map<String, Set<String>> directFanIn = new HashMap<>();
		Map<String, Set<String>> directFanOut = new HashMap<>();

		// Préparer les noms complets de tables
		for (JdbcModel.JdbcSchema schema : model.schemas()) {
			for (JdbcTable table : schema.tables()) {
				String fullName = schema.name() + "." + table.name();
				tableMap.put(fullName, table);
				directFanIn.put(fullName, new HashSet<>());
				directFanOut.put(fullName, new HashSet<>());
			}
		}

		// Analyse structurelle + dépendances
		for (JdbcModel.JdbcSchema schema : model.schemas()) {
			for (JdbcTable table : schema.tables()) {
				String tableName = schema.name() + "." + table.name();

				// Clé primaire
				if (table.columns().stream().noneMatch(JdbcModel.JdbcColumn::isPrimaryKey)) {
					tablesWithoutPrimaryKey.add(tableName);
				}

				for (JdbcModel.JdbcColumn column : table.columns()) {
					if (!column.nullable() && column.defaultValue() == null) {
						nonNullableColumnsWithoutDefault.add(tableName + "." + column.name());
					}
					if (column.comment() == null || column.comment().isBlank()) {
						undocumentedObjects.add("Column: " + tableName + "." + column.name());
					}
				}

				// Relations
				for (JdbcModel.JdbcRelation relation : table.relations()) {
					String targetTable = schema.name() + "." + relation.targetTable();
					directFanOut.get(tableName).add(targetTable);
					directFanIn.computeIfAbsent(targetTable, k -> new HashSet<>()).add(tableName);

					JdbcTable tgt = tableMap.get(targetTable);
					if (tgt == null || tgt.columns().stream().noneMatch(c -> c.name().equals(relation.targetColumn()))) {
						invalidRelations.add("Relation " + relation.name() + " in " + tableName +
								" -> missing target " + relation.targetTable() + "." + relation.targetColumn());
					}
				}

				// Indexes
				Set<Set<String>> seenIndexes = new HashSet<>();
				for (JdbcModel.JdbcIndex index : table.indexes()) {
					Set<String> cols = new HashSet<>(index.columnNames());
					if (!seenIndexes.add(cols)) {
						redundantIndexes.add(tableName + "." + index.name());
					}
				}

				// Contraintes triviales
				for (JdbcModel.JdbcConstraint constraint : table.constraints()) {
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
		Map<String, Integer> transitiveFanIn = computeTransitiveFanIn(directFanIn);

		// Construction des stats finales
		Map<String, JdbcModelAnalysisReport.TableDependencyStats> dependencyStats = new HashMap<>();
		for (String tableName : tableMap.keySet()) {
			int fanIn = directFanIn.getOrDefault(tableName, Set.of()).size();
			int fanOut = directFanOut.getOrDefault(tableName, Set.of()).size();
			int fanInTrans = transitiveFanIn.getOrDefault(tableName, 0);
			dependencyStats.put(tableName, new JdbcModelAnalysisReport.TableDependencyStats(fanIn, fanOut, fanInTrans));
		}

		return new JdbcModelAnalysisReport(
				tablesWithoutPrimaryKey,
				nonNullableColumnsWithoutDefault,
				invalidRelations,
				redundantIndexes,
				trivialCheckConstraints,
				undocumentedObjects,
				dependencyStats);
	}

	private static Map<String, Integer> computeTransitiveFanIn(Map<String, Set<String>> reverseGraph) {
		Map<String, Integer> result = new HashMap<>();
		for (String target : reverseGraph.keySet()) {
			Set<String> visited = new HashSet<>();
			Deque<String> stack = new ArrayDeque<>(reverseGraph.getOrDefault(target, Set.of()));
			while (!stack.isEmpty()) {
				String source = stack.pop();
				if (visited.add(source)) {
					stack.addAll(reverseGraph.getOrDefault(source, Set.of()));
				}
			}
			result.put(target, visited.size());
		}
		return result;
	}
}
