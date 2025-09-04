package io.vertigo.shell.commands.db;

import io.vertigo.shell.commands.db.model.DbModel;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.mermaid.ShinyMermaidServer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.util.concurrent.Callable;

@Command(name = "mermaid", description = "Launches a Mermaid server to visualize the DB model.")
public class DbModelMermaidCommand implements Callable<Integer> {

    @Option(names = {"-p", "--port"}, description = "Port for the Mermaid server (default: 8080)", defaultValue = "8080")
    private int port;

    // This DbModel needs to be loaded. For a real scenario, it would come from a DB connection.
    // For this example, we'll use a mock DbModel.
    // In a real shell, DbModel would be part of the shell's context or loaded via other commands.
    private DbModel mockDbModel; // Placeholder for the DbModel

    // Constructor for Picocli
    public DbModelMermaidCommand() {
        // Initialize a mock DbModel for demonstration
        mockDbModel = createMockDbModel();
    }

    @Override
    public Integer call() throws Exception {
        ShinyMermaidServer server = new ShinyMermaidServer(Shiny.INSTANCE, port); // Assuming Shiny.INSTANCE is accessible
        try {
            server.start();
            server.updateDiagram(mockDbModel); // Push the initial diagram

            // Keep the server running until interrupted
            Thread.currentThread().join(); // This will block until Ctrl+C or external termination
        } catch (IOException e) {
            Shiny.INSTANCE.getWriter().println("Error starting Mermaid server: " + e.getMessage());
            return 1; // Indicate error
        } finally {
            server.stop();
        }
        return 0; // Success
    }

    private DbModel createMockDbModel() {
        // This is a simplified mock DbModel for testing the Mermaid server.
        // In a real application, this would be loaded from a database.
        DbModel model = new DbModel();

        DbModel.JdbcSchema schema1 = new DbModel.JdbcSchema("APP_SCHEMA");
        DbModel.JdbcTable usersTable = new DbModel.JdbcTable("USERS");
        usersTable.addColumn(new DbModel.JdbcColumn("ID", "VARCHAR", true, true));
        usersTable.addColumn(new DbModel.JdbcColumn("NAME", "VARCHAR", false, false));
        usersTable.addColumn(new DbModel.JdbcColumn("EMAIL", "VARCHAR", false, true)); // Unique
        schema1.addTable(usersTable);

        DbModel.JdbcTable productsTable = new DbModel.JdbcTable("PRODUCTS");
        productsTable.addColumn(new DbModel.JdbcColumn("PRODUCT_ID", "INTEGER", true, true));
        productsTable.addColumn(new DbModel.JdbcColumn("NAME", "VARCHAR", false, false));
        productsTable.addColumn(new DbModel.JdbcColumn("PRICE", "DECIMAL", false, false));
        schema1.addTable(productsTable);

        DbModel.JdbcTable ordersTable = new DbModel.JdbcTable("ORDERS");
        ordersTable.addColumn(new DbModel.JdbcColumn("ORDER_ID", "INTEGER", true, true));
        ordersTable.addColumn(new DbModel.JdbcColumn("USER_ID", "VARCHAR", false, false));
        ordersTable.addColumn(new DbModel.JdbcColumn("ORDER_DATE", "DATE", false, false));
        ordersTable.addRelation(new DbModel.JdbcRelation("USERS", "FK_ORDERS_USERS")); // FK to USERS
        schema1.addTable(ordersTable);

        DbModel.JdbcTable orderItemsTable = new DbModel.JdbcTable("ORDER_ITEMS");
        orderItemsTable.addColumn(new DbModel.JdbcColumn("ITEM_ID", "INTEGER", true, true));
        orderItemsTable.addColumn(new DbModel.JdbcColumn("ORDER_ID", "INTEGER", false, false));
        orderItemsTable.addColumn(new DbModel.JdbcColumn("PRODUCT_ID", "INTEGER", false, false));
        orderItemsTable.addColumn(new DbModel.JdbcColumn("QUANTITY", "INTEGER", false, false));
        orderItemsTable.addRelation(new DbModel.JdbcRelation("ORDERS", "FK_ORDER_ITEMS_ORDERS")); // FK to ORDERS
        orderItemsTable.addRelation(new DbModel.JdbcRelation("PRODUCTS", "FK_ORDER_ITEMS_PRODUCTS")); // FK to PRODUCTS
        schema1.addTable(orderItemsTable);

        model.addSchema(schema1);

        return model;
    }
}
