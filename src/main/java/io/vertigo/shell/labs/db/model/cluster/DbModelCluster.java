package io.vertigo.shell.labs.db.model.cluster;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vertigo.shell.labs.db.model.DbModel;

/**
 * Analyseur de clusters pour regrouper les tables JDBC selon différents algorithmes.
 * - Par schéma (BY_SCHEMA) — simple et utile pour visualisation.
 * - Composants fortement connexes (STRONGLY_CONNECTED_COMPONENTS) — détecte des modules fortement couplés via Tarjan.
 * - Par densité (BY_DENSITY) — heuristique basée sur les degrés de connexion.
 */
final class DbModelCluster {

	/**
	 * Représente un cluster de tables.
	 */
	record JdbcCluster(String name, Set<String> tableNames) {
	}

	/**
	 * Analyse le modèle pour produire des clusters selon la stratégie choisie.
	 */
	static List<JdbcCluster> analyze(final DbModel model, final DbModelClusterStrategy strategy) {
		return switch (strategy) {
			case BY_SCHEMA -> clusterBySchema(model);
			case STRONGLY_CONNECTED_COMPONENTS -> clusterByStronglyConnectedComponents(model);
			case BY_DENSITY -> clusterByDensity(model);
		};
	}

	private static List<JdbcCluster> clusterBySchema(final DbModel model) {
		final List<JdbcCluster> clusters = new ArrayList<>();
		for (final DbModel.JdbcSchema schema : model.schemas()) {
			final Set<String> tableNames = new HashSet<>();
			for (final DbModel.JdbcTable table : schema.tables()) {
				tableNames.add(schema.name() + "." + table.name());
			}
			clusters.add(new JdbcCluster("schema:" + schema.name(), tableNames));
		}
		return clusters;
	}

	private static List<JdbcCluster> clusterByStronglyConnectedComponents(final DbModel model) {
		final Map<String, Set<String>> graph = new HashMap<>();
		for (final DbModel.JdbcSchema schema : model.schemas()) {
			for (final DbModel.JdbcTable table : schema.tables()) {
				final String source = schema.name() + "." + table.name();
				graph.putIfAbsent(source, new HashSet<>());
				for (final DbModel.JdbcRelation rel : table.relations()) {
					final String target = schema.name() + "." + rel.targetTable();
					graph.get(source).add(target);
				}
			}
		}
		return tarjansAlgorithm(graph);
	}

	private static List<JdbcCluster> tarjansAlgorithm(final Map<String, Set<String>> graph) {
		final List<JdbcCluster> clusters = new ArrayList<>();
		final Map<String, Integer> indexMap = new HashMap<>();
		final Map<String, Integer> lowLinkMap = new HashMap<>();
		final Deque<String> stack = new ArrayDeque<>();
		final Set<String> onStack = new HashSet<>();
		final int[] index = { 0 };

		for (final String node : graph.keySet()) {
			if (!indexMap.containsKey(node)) {
				strongConnect(node, graph, indexMap, lowLinkMap, stack, onStack, index, clusters);
			}
		}
		return clusters;
	}

	private static void strongConnect(final String v, final Map<String, Set<String>> graph,
			final Map<String, Integer> indexMap, final Map<String, Integer> lowLinkMap,
			final Deque<String> stack, final Set<String> onStack, final int[] index,
			final List<JdbcCluster> clusters) {
		indexMap.put(v, index[0]);
		lowLinkMap.put(v, index[0]);
		index[0]++;
		stack.push(v);
		onStack.add(v);

		for (final String w : graph.getOrDefault(v, Set.of())) {
			if (!indexMap.containsKey(w)) {
				strongConnect(w, graph, indexMap, lowLinkMap, stack, onStack, index, clusters);
				lowLinkMap.put(v, Math.min(lowLinkMap.get(v), lowLinkMap.get(w)));
			} else if (onStack.contains(w)) {
				lowLinkMap.put(v, Math.min(lowLinkMap.get(v), indexMap.get(w)));
			}
		}

		if (lowLinkMap.get(v).equals(indexMap.get(v))) {
			final Set<String> component = new HashSet<>();
			String w;
			do {
				w = stack.pop();
				onStack.remove(w);
				component.add(w);
			} while (!w.equals(v));
			clusters.add(new JdbcCluster("SCC_" + clusters.size(), component));
		}
	}

	private static List<JdbcCluster> clusterByDensity(final DbModel model) {
		// Heuristique simple : cluster des tables avec fan-in + fan-out > seuil
		final Map<String, Set<String>> graph = new HashMap<>();
		for (final DbModel.JdbcSchema schema : model.schemas()) {
			for (final DbModel.JdbcTable table : schema.tables()) {
				final String source = schema.name() + "." + table.name();
				graph.putIfAbsent(source, new HashSet<>());
				for (final DbModel.JdbcRelation rel : table.relations()) {
					final String target = schema.name() + "." + rel.targetTable();
					graph.get(source).add(target);
				}
			}
		}
		// Classement naïf par degré
		final Map<String, Integer> degree = new HashMap<>();
		for (final Map.Entry<String, Set<String>> entry : graph.entrySet()) {
			degree.put(entry.getKey(), entry.getValue().size());
			for (final String tgt : entry.getValue()) {
				degree.put(tgt, degree.getOrDefault(tgt, 0) + 1);
			}
		}
		final List<String> sorted = new ArrayList<>(degree.keySet());
		sorted.sort(Comparator.comparingInt(degree::get).reversed());

		final List<JdbcCluster> clusters = new ArrayList<>();
		final Set<String> assigned = new HashSet<>();
		for (final String table : sorted) {
			if (assigned.contains(table)) {
				continue;
			}
			final Set<String> cluster = new HashSet<>();
			cluster.add(table);
			for (final String neighbor : graph.getOrDefault(table, Set.of())) {
				if (!assigned.contains(neighbor)) {
					cluster.add(neighbor);
					assigned.add(neighbor);
				}
			}
			assigned.add(table);
			clusters.add(new JdbcCluster("Dense_" + clusters.size(), cluster));
		}
		return clusters;
	}
}
