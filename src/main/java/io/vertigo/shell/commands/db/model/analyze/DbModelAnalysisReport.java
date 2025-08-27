package io.vertigo.shell.commands.db.model.analyze;

import java.util.List;
import java.util.Map;

record DbModelAnalysisReport(
		List<String> tablesWithoutPrimaryKey,
		List<String> nonNullableColumnsWithoutDefault,
		List<String> invalidRelations,
		List<String> redundantIndexes,
		List<String> trivialCheckConstraints,
		List<String> undocumentedObjects,
		Map<String, TableDependencyStats> tableDependencyStats) {

	record TableDependencyStats(int fanIn, int fanOut, int transitiveFanIn) {
	}
}
