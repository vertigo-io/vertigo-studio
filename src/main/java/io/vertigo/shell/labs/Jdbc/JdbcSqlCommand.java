package io.vertigo.shell.labs.Jdbc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ai.Agent;
import io.vertigo.shell.ai.AgentBuilder;
import io.vertigo.shell.labs.Jdbc.model.JdbcModel;
import io.vertigo.shell.labs.Jdbc.model.JdbcModel.JdbcColumn;
import io.vertigo.shell.labs.Jdbc.model.JdbcModel.JdbcRelation;
import io.vertigo.shell.labs.Jdbc.model.JdbcModel.JdbcSchema;
import io.vertigo.shell.labs.Jdbc.model.JdbcModel.JdbcTable;
import io.vertigo.shell.labs.Jdbc.model.JdbcModelAnalysisReport;
import io.vertigo.shell.labs.Jdbc.model.JdbcModelAnalyzer;
import io.vertigo.shell.labs.Jdbc.model.JdbcModelClusterAnalyzer;
import io.vertigo.shell.labs.Jdbc.model.JdbcModelClusterAnalyzer.JdbcCluster;
import io.vertigo.shell.labs.Jdbc.model.JdbcModelClusterAnalyzer.Strategy;
import io.vertigo.shell.labs.Jdbc.model.JdbcModelLoader;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.ShinyColors;
import io.vertigo.shell.shiny.ShinyTree;
import io.vertigo.shell.shiny.ShinyTreeNode;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "sql", description = "Executes a SQL query")
public final class JdbcSqlCommand implements ShellCommand {

	@Option(names = { "--model", "-m" }, description = "SQL load model (schemas, tables..)")
	private boolean load;

	@Option(names = { "--fancy", "-f" }, description = "SQL create a fancy set of 10 data for a table")
	private boolean fancy;

	@Option(names = { "--analyze", "-a" }, description = "SQL analyze model")
	private boolean analyze;

	@Option(names = { "--cluster", "-c" }, description = "SQL cluster tables")
	private boolean cluster;

	@Option(names = { "--tables", "-t" }, description = "SQL list tables")
	private boolean tables;

	@Option(names = { "--stats", "-s" }, description = "SQL list stats")
	private boolean stats;

	@Option(names = { "--table", "-T" }, description = "SQL describe table")
	private String tableName;

	@Option(names = { "--help", "-h" }, usageHelp = true, description = "Show help for sql command")
	private boolean help;

	@Override
	public void run() {
		if (help) {
			// Picocli will print usage
			return;
		}
		if (JdbcContext.connection == null) {
			System.err.println("Not connected. Use 'connect' first.");
			return;
		}

		try {
			if (load) {
				loadModel();
			}
			if (tables) {
				listTables();
			}
			if (tableName != null) {
				describeTable();
			}
		} catch (SQLException e) {
			System.err.println("Error : " + e.getMessage());
		}
		if (analyze) {
			analyzeModel();
		}
		if (cluster) {
			cluster();
		}
		if (stats) {
			stats();
		}
		if (fancy) {
			fancy();
		}

	}

	@Override
	public void reset() {
		load = false;
		analyze = false;
		cluster = false;
		tables = false;
		fancy = false;
		stats = false;
		tableName = null;
		help = false;
	}

	private void fancy() {
		final Agent agent = new AgentBuilder().build();
		if (JdbcContext.model == null) {
			throw new VSystemException("The model must de loaded before analyze");
		}
		JdbcTable tableXX = null;
		for (JdbcModel.JdbcSchema schema : JdbcContext.model.schemas()) {
			for (JdbcTable table : schema.tables()) {
				if ("artist".equals(table.name()))
					tableXX = table;
			}
		}
		StringBuilder info = new StringBuilder()
				.append("table : ").append(tableName);
		for (JdbcColumn column : tableXX.columns()) {
			info
					.append("{ column ")
					.append("name : ").append(column.name()).append(", ")
					.append("primaryKey : ").append(column.isPrimaryKey()).append(", ")
					.append("nullable : ").append(column.nullable()).append(", ")
					.append("type : ").append(column.typeName())
					.append(" }, ");
		}
		String query = """
				Crée un insert SQL pour remplir la table artist
				dont la structure est la suivants :"
				""" + info.toString()
				+ """
						utilise des noms d'artistes pop connus de tous
							""";
		String response = agent.answer(query);
		System.out.println(response);
	}

	private void loadModel() throws SQLException {
		if (JdbcContext.model == null) {
			JdbcContext.model = new JdbcModelLoader(JdbcContext.connection).loadModel();
		}
		final ShinyTree tree = Shiny.tree("model");
		for (JdbcSchema schema : JdbcContext.model.schemas()) {
			final ShinyTreeNode schemaNode = tree.getRoot().addNode("schema : " + schema.name());
			final ShinyTreeNode tablesNode = schemaNode.addNode("tables (" + schema.tables().size() + ")");
			for (JdbcTable table : schema.tables()) {
				final ShinyTreeNode tableNode = tablesNode.addNode(table.name());
				final ShinyTreeNode columnsNode = tableNode.addNode("columns");
				for (JdbcColumn column : table.columns()) {

					String info = column.name() + " " + ShinyColors.GREEN_BRIGHT + column.typeName() + ShinyColors.RESET;

					if (column.isPrimaryKey()) {
						info = ShinyColors.UNDERLINE + info + ShinyColors.RESET;
					}
					columnsNode.addNode(info);
				}
			}
		}

		tree.print();
	}

	private void stats() {
		if (JdbcContext.model == null) {
			throw new VSystemException("The model must de loaded before analyze");
		}

		final List<String> tableNames = new ArrayList<String>();
		final List<Integer> tableCounts = new ArrayList<Integer>();

		for (JdbcSchema schema : JdbcContext.model.schemas()) {
			for (JdbcTable table : schema.tables()) {
				String query = "select count(*) as count from " + table.name();
				try (Statement stmt = JdbcContext.connection.createStatement()) {
					try (ResultSet rs = stmt.executeQuery(query)) {
						rs.next();
						tableNames.add(table.name());
						tableCounts.add(rs.getInt(1));
					}
				} catch (final SQLException e) {
					throw new VSystemException(e, "Failed to execute SQL query: {0}", e.getMessage());
				}
			}
		}

		Shiny.barChart()
				.title("Tables Row Count")
				.header(tableNames)
				.rows(tableCounts)
				.print(100);
	}

	private void listTables() throws SQLException {
		final DatabaseMetaData metaData = JdbcContext.connection.getMetaData();
		final List<String[]> rows = new ArrayList<>();
		try (final ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" })) {
			while (rs.next()) {
				rows.add(new String[] { rs.getString("TABLE_NAME") });
			}
		}
		Shiny.table()
				.title("Tables in the database:")
				.noDataFound("No tables found in the database.")
				.header("TABLE_NAME")
				.rows(rows)
				.print();
	}

	private void cluster() {
		if (JdbcContext.model == null) {
			throw new VSystemException("The model must de loaded before analyze");
		}
		List<JdbcCluster> clusters = JdbcModelClusterAnalyzer.analyze(JdbcContext.model, Strategy.BY_DENSITY);

		ShinyTree tree = Shiny.tree("Clusters");
		for (JdbcCluster cluster : clusters) {
			var node = tree.getRoot().addNode(cluster.name());
			for (String tableName : cluster.tableNames()) {
				node.addNode(tableName);
			}
		}
		tree.print();
	}

	private void analyzeModel() {
		if (JdbcContext.model == null) {
			throw new VSystemException("The model must de loaded before analyze");
		}
		int tables = 0;
		int columns = 0;
		int relations = 0;

		for (JdbcSchema schema : JdbcContext.model.schemas()) {
			for (JdbcTable table : schema.tables()) {
				tables++;
				for (JdbcColumn column : table.columns()) {
					columns++;
				}
				for (JdbcRelation relation : table.relations()) {
					relations++;
				}
			}
		}
		String[] result = { "" + tables, "" + columns, "" + relations };
		List<String[]> rows = new ArrayList<>();
		rows.add(result);

		Shiny.table()
				.title("Objects in the database:")
				.noDataFound("No object found in the database.")
				.header("Table", "Column", "Relations")
				.rows(rows)
				.print();
		//A linear 
		int complexity = 10 * tables + 1 * columns + relations * 3;
		System.out.println("Complexity :" + complexity);

		//--------------------------------------------
		//------ANALYZER------------------------------
		//--------------------------------------------
		JdbcModelAnalysisReport report = JdbcModelAnalyzer.analyze(JdbcContext.model);
		//lister ici toutes les anomalies détectées
		report.tableDependencyStats().forEach((table, stats) -> {
			System.out.printf("Table %s: fanIn=%d, fanOut=%d, fanInTransitive=%d%n",
					table, stats.fanIn(), stats.fanOut(), stats.transitiveFanIn());
		});
	}

	private void describeTable() throws SQLException {
		final DatabaseMetaData metaData = JdbcContext.connection.getMetaData();
		final List<String[]> columns = new ArrayList<>();
		try (final ResultSet rs = metaData.getColumns(null, null, tableName, "%")) {
			while (rs.next()) {
				String[] column = {
						rs.getString("COLUMN_NAME"),
						rs.getString("TYPE_NAME"),
						rs.getString("COLUMN_SIZE"),
						rs.getString("IS_NULLABLE")
				};
				columns.add(column);
			}
		}
		Shiny.table()
				.title("Structure of table " + tableName + ":")
				.noDataFound("\"Table '\" + tableName + '\' not found or has no columns.\"")
				.header("Name", "Type", "Size", "Nullable")
				.rows(columns)
				.print();
	}
}
