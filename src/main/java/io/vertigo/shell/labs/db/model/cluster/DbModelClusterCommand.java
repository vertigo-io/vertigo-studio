package io.vertigo.shell.labs.db.model.cluster;

import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.db.DbContext;
import io.vertigo.shell.labs.db.model.cluster.DbModelCluster.JdbcCluster;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.tree.ShinyTree;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "cluster", description = "Cluster tables in the model")
public final class DbModelClusterCommand implements ShellCommand {
	@Option(
			names = { "-s", "--strategy" },
			description = "Clustering strategy: ${COMPLETION-CANDIDATES} (default: ${DEFAULT-VALUE})",
			defaultValue = "STRONGLY_CONNECTED_COMPONENTS")
	private DbModelClusterStrategy strategy;

	@Override
	public void run() {
		final List<JdbcCluster> clusters = DbModelCluster.analyze(DbContext.model(), DbModelClusterStrategy.STRONGLY_CONNECTED_COMPONENTS);

		final ShinyTree tree = Shiny.tree("Clusters");
		for (JdbcCluster cluster : clusters) {
			var node = tree.getRoot().addNode(cluster.name());
			for (String tableName : cluster.tableNames()) {
				node.addNode(tableName);
			}
		}
		tree.print();
	}

}
