package io.vertigo.shell.systems.db.commands.cluster;

/**
 * Type de stratégie de clustering
 */
public enum DbClusterStrategy {
	BY_SCHEMA,
	STRONGLY_CONNECTED_COMPONENTS, //SCC
	BY_DENSITY
}
