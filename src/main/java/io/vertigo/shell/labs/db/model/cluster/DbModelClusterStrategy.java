package io.vertigo.shell.labs.db.model.cluster;

/**
 * Type de stratégie de clustering
 */
public enum DbModelClusterStrategy {
	BY_SCHEMA,
	STRONGLY_CONNECTED_COMPONENTS, //SCC
	BY_DENSITY
}
