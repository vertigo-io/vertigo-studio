package io.vertigo.shell.commands.db.model.cluster.attraction;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import io.vertigo.core.lang.Assertion;

public final class AttractionClusterer {
	private final List<Node> nodes;
	private final List<Edge> edges;
	private final double repulsionConstant; // Strength of repulsion force
	private final double attractionConstant; // Strength of attraction force
	private final double dampingFactor; // Reduces velocity over time
	private final int iterations; // Number of simulation steps

	public AttractionClusterer(List<Node> nodes, List<Edge> edges,
			double repulsionConstant, double attractionConstant,
			double dampingFactor, int iterations) {
		Assertion.check().isNotNull(nodes);
		Assertion.check().isNotNull(edges);
		Assertion.check().isTrue(repulsionConstant > 0, "Repulsion constant must be positive");
		Assertion.check().isTrue(attractionConstant > 0, "Attraction constant must be positive");
		Assertion.check().isTrue(dampingFactor >= 0 && dampingFactor <= 1, "Damping factor must be between 0 and 1");
		Assertion.check().isTrue(iterations > 0, "Iterations must be positive");

		this.nodes = nodes;
		this.edges = edges;
		this.repulsionConstant = repulsionConstant;
		this.attractionConstant = attractionConstant;
		this.dampingFactor = dampingFactor;
		this.iterations = iterations;

		// Initialize node positions randomly if not already set
		Random random = new Random();
		for (Node node : nodes) {
			if (node.getX() == 0 && node.getY() == 0) { // Assuming 0,0 is default uninitialized
				node.setX(random.nextDouble() * 100);
				node.setY(random.nextDouble() * 100);
			}
		}
	}

	public Map<String, double[]> cluster() {
		for (int i = 0; i < iterations; i++) {
			// Calculate forces
			for (Node node : nodes) {
				double totalFx = 0;
				double totalFy = 0;

				// Repulsion forces from all other nodes
				for (Node otherNode : nodes) {
					if (node != otherNode) {
						double dx = node.getX() - otherNode.getX();
						double dy = node.getY() - otherNode.getY();
						double distance = Math.sqrt(dx * dx + dy * dy);
						if (distance < 0.1)
							distance = 0.1; // Avoid division by zero or very large forces

						double force = repulsionConstant / (distance * distance); // Coulomb's Law
						totalFx += force * (dx / distance);
						totalFy += force * (dy / distance);
					}
				}

				// Attraction forces from connected nodes (edges)
				for (Edge edge : edges) {
					Node connectedNode = null;
					if (edge.getSource() == node) {
						connectedNode = edge.getTarget();
					} else if (edge.getTarget() == node) {
						connectedNode = edge.getSource();
					}

					if (connectedNode != null) {
						double dx = connectedNode.getX() - node.getX();
						double dy = connectedNode.getY() - node.getY();
						double distance = Math.sqrt(dx * dx + dy * dy);
						if (distance < 0.1)
							distance = 0.1;

						// Hooke's Law (spring-like)
						double force = attractionConstant * edge.getStrength() * (distance - edge.getIdealLength());
						totalFx += force * (dx / distance);
						totalFy += force * (dy / distance);
					}
				}
				node.applyForce(totalFx, totalFy);
			}

			// Update positions and apply damping
			for (Node node : nodes) {
				node.updatePosition();
				node.applyDamping(dampingFactor);
			}
		}

		// Return clustered positions
		return nodes.stream()
				.collect(Collectors.toMap(Node::getId, node -> new double[] { node.getX(), node.getY() }));
	}
}
