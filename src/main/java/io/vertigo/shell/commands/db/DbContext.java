package io.vertigo.shell.commands.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.commands.db.model.DbModel;

public final class DbContext {
	public static Connection dbConnection;
	private static DbModel dbModel;
	public static Properties secrets = buildSecrets();

	public static void model(final DbModel model) {
		Assertion.check().isNotNull(model);
		//---
		dbModel = model;
	}

	public static void connection(final Connection connection) {
		Assertion.check().isNotNull(connection);
		//---
		dbConnection = connection;
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

	private static Properties buildSecrets() {
		final var mySecrets = new Properties();
		final String userHome = System.getProperty("user.home");
		final String filePath = userHome + File.separator + "vortex.txt";

		System.out.println("loading secrets");
		try (FileInputStream fis = new FileInputStream(filePath)) {
			mySecrets.load(fis);
			System.out.println("secrets found :" + mySecrets.size());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return mySecrets;
	}
}
