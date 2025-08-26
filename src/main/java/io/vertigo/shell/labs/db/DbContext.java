package io.vertigo.shell.labs.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.VSystemException;
import io.vertigo.shell.labs.db.model.DbModel;

public final class DbContext {
	public static Connection dbConnection;
	private static DbModel dbModel;
	public static Properties secrets = buildSecrets();

	public static void model(DbModel model) {
		Assertion.check().isNotNull(model);
		//---
		dbModel = model;
	}

	public static void connection(Connection connection) {
		Assertion.check().isNotNull(connection);
		//---
		dbConnection = connection;
	}

	public static Connection connection() {
		if (dbConnection == null) {
			throw new VSystemException("Not connected. Use 'connect' first.");
		}
		return dbConnection;
	}

	public static DbModel model() {
		if (DbContext.dbModel == null) {
			throw new VSystemException("The model must de loaded before save");
		}
		return dbModel;
	}

	private static Properties buildSecrets() {
		var mySecrets = new Properties();
		final String userHome = System.getProperty("user.home");
		final String filePath = userHome + File.separator + "vortex.txt";

		System.out.println("loading secrets");
		try (FileInputStream fis = new FileInputStream(filePath)) {
			mySecrets.load(fis);
			System.out.println("secrets found :" + mySecrets.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mySecrets;
	}
}
