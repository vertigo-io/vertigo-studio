package io.vertigo.shell.labs.Jdbc;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.labs.Jdbc.JdbcModel.JdbcColumn;
import io.vertigo.shell.labs.Jdbc.JdbcModel.JdbcRelation;
import io.vertigo.shell.labs.Jdbc.JdbcModel.JdbcSchema;
import io.vertigo.shell.labs.Jdbc.JdbcModel.JdbcTable;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.ShinyColors;
import io.vertigo.shell.shiny.ShinyNode;
import io.vertigo.shell.shiny.ShinyTree;

@Parameters(commandNames = "jdbc-sql", commandDescription = "Executes a SQL query")
public final class JdbcSqlCommand implements ShellCommand {
	@Parameter(names = { "--query", "-q" }, description = "SQL query to execute")
	private String query;

	@Parameter(names = { "--model", "-m" }, description = "SQL load model (schemas, tables..)")
	private boolean load;

	@Parameter(names = { "--analyze", "-a" }, description = "SQL analyze model")
	private boolean analyze;

	@Parameter(names = { "--tables", "-t" }, description = "SQL list tables")
	private boolean tables;

	@Parameter(names = { "--stats", "-s" }, description = "SQL list stats")
	private boolean stats;

	@Parameter(names = { "--ping", "-p" }, description = "SQL ping database")
	private boolean ping;

	@Parameter(names = { "--table", "-T" }, description = "SQL describe table")
	private String tableName;

	@Parameter(names = { "--help", "-h" }, description = "Show help for sql command")
	private boolean help;

	public void run() throws Exception {
		if (help) {
			// JCommander will print usage
			return;
		}
		if (JdbcContext.connection == null) {
			System.out.println("Not connected. Use 'connect' first.");
			return;
		}
		if (query != null) {
			executeQuery();
		}
		if (load) {
			loadModel();
		}
		if (analyze) {
			analyzeModel();
		}
		if (stats) {
			stats();
		}
		if (tables) {
			listTables();
		}
		if (ping) {
			ping();
		}
		if (tableName != null) {
			describeTable();
		}
	}

	@Override
	public void reset() {
		query = null;
		load = false;
		analyze = false;
		tables = false;
		stats = false;
		ping = false;
		tableName = null;
		help = false;
	}

	private void executeQuery() {
		try (Statement stmt = JdbcContext.connection.createStatement()) {
			if (query.trim().toLowerCase().startsWith("select")) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					printResultSet(rs);
				}
			} else {
				final int rowsAffected = stmt.executeUpdate(query);
				System.out.println(rowsAffected + " row(s) affected.");
			}
			query = null;
		} catch (final SQLException e) {
			throw new VSystemException(e, "Failed to execute SQL query: {0}", e.getMessage());
		}
	}

	private void printResultSet(final ResultSet rs) throws SQLException {
		final ResultSetMetaData rsmd = rs.getMetaData();
		final int columnsNumber = rsmd.getColumnCount();

		// Récupération des en-têtes
		final String[] header = new String[columnsNumber];
		for (int i = 1; i <= columnsNumber; i++) {
			header[i - 1] = rsmd.getColumnName(i);
		}

		// Récupération des données
		List<String[]> rows = new ArrayList<>();
		while (rs.next()) {
			String[] row = new String[columnsNumber];
			for (int i = 1; i <= columnsNumber; i++) {
				String value = rs.getString(i);
				row[i - 1] = value != null ? value : "NULL";
			}
			rows.add(row);
		}

		Shiny.table()
				.title("Result of query:")
				.noDataFound("No data found")
				.header(header)
				.rows(rows)
				.print();
	}

	private void ping() throws Exception {
		long startTime = System.nanoTime();
		JdbcContext.connection.isValid(2);
		long endTime = System.nanoTime();
		System.out.println("Ping: " + (endTime - startTime) / 1_000_000.0 + " ms");
	}

	private void loadModel() throws Exception {
		if (JdbcContext.model == null) {
			JdbcContext.model = new JdbcModelLoader(JdbcContext.connection).loadModel();
		}
		final ShinyTree tree = Shiny.tree("model");
		for (JdbcSchema schema : JdbcContext.model.schemas()) {
			final ShinyNode schemaNode = tree.getRoot().addNode("schema : " + schema.name());
			final ShinyNode tablesNode = schemaNode.addNode("tables (" + schema.tables().size() + ")");
			for (JdbcTable table : schema.tables()) {
				final ShinyNode tableNode = tablesNode.addNode(table.name());
				final ShinyNode columnsNode = tableNode.addNode("columns");
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

	private void stats() throws Exception {
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

	private void listTables() throws Exception {
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

	private void analyzeModel() throws Exception {
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
	}

	private void describeTable() throws Exception {
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
				.noDataFound("\"Table '\" + tableName + \"' not found or has no columns.\"")
				.header("Name", "Type", "Size", "Nullable")
				.rows(columns)
				.print();
	}
}
