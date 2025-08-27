package io.vertigo.shell.commands.db.model.cluster;

/**
 * Type de stratégie de clustering
 */
public enum DbModelClusterStrategy {
	BY_SCHEMA,
	STRONGLY_CONNECTED_COMPONENTS, //SCC
	BY_DENSITY
}
