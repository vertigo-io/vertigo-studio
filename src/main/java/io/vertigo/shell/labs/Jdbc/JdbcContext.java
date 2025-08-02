package io.vertigo.shell.labs.Jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public final class JdbcContext {
	static Connection connection;
	static JdbcModel model;
	public static Properties secrets = buildSecrets();

	private static Properties buildSecrets() {
		var mySecrets = new Properties();
		String userHome = System.getProperty("user.home");
		String filePath = userHome + File.separator + "vortex.txt";

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
