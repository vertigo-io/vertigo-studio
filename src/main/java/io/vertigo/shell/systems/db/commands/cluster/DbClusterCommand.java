package io.vertigo.shell.systems.db.commands.cluster;

import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.db.DbContext;
import io.vertigo.shell.systems.db.commands.cluster.DbCluster.JdbcCluster;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.data.tree.ShinyTree;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "cluster", description = "Cluster tables in the model")
public final class DbClusterCommand implements ShellCommand {
	@Option(
			names = { "-s", "--strategy" },
			description = "Clustering strategy: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})",
			defaultValue = "STRONGLY_CONNECTED_COMPONENTS")
	private DbClusterStrategy strategy;

	@Override
	public void run() {
		final ShinyWriter writer = Shiny.writer();

		final List<JdbcCluster> clusters = DbCluster.analyze(DbContext.model(), DbClusterStrategy.STRONGLY_CONNECTED_COMPONENTS);

		final ShinyTree tree = Shiny.tree("Clusters");
		for (final JdbcCluster cluster : clusters) {
			final var node = tree.getRoot().addChild(cluster.name());
			for (final String tableName : cluster.tableNames()) {
				node.addChild(tableName);
			}
		}
		tree.render(writer);
	}

}
