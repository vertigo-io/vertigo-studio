package io.vertigo.shell.systems.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import jakarta.annotation.Nonnull;

public final class DbContext {
	public static Connection dbConnection;
	private static DbModel dbModel;

	public static void model(@Nonnull final DbModel model) {
		Assertion.check().isNotNull(model);
		//---
		dbModel = model;
	}

	public static void connect(final String url, final String user, String password) throws SQLException {
		final Properties props = new Properties();
		props.setProperty("user", user);
		props.setProperty("password", password);
		//---
		dbConnection = DriverManager.getConnection(url, props);
		dbConnection.setReadOnly(true);
	}

	public static void disconnect() throws SQLException {
		if (dbConnection != null) {
			dbConnection.close();
		}
		dbConnection = null;
	}

	public static boolean isConnected() {
		return dbConnection != null;
	}

	public static Connection connection() {
		if (dbConnection == null) {
			throw new VSystemException("Not connected. Use 'connect' first.");
		}
		return dbConnection;
	}

	public static DbModel model() {
		if (dbModel == null) {
			throw new VSystemException("The model must de loaded before usage");
		}
		return dbModel;
	}
}
