package io.vertigo.shell.labs.Jdbc.model;

import java.util.List;
import java.util.Map;

public record JdbcModelAnalysisReport(
		List<String> tablesWithoutPrimaryKey,
		List<String> nonNullableColumnsWithoutDefault,
		List<String> invalidRelations,
		List<String> redundantIndexes,
		List<String> trivialCheckConstraints,
		List<String> undocumentedObjects,
		Map<String, TableDependencyStats> tableDependencyStats) {

	public record TableDependencyStats(int fanIn, int fanOut, int transitiveFanIn) {
	}
}
