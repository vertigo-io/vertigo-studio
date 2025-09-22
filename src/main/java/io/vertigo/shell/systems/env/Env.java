package io.vertigo.shell.systems.env;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class Env {
	public static Properties secrets = buildSecrets();

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

	public static String get(EnvVar varName) {
		return secrets.getProperty(varName.getKey());
	}

	public static void set(String key, String value) {
		secrets.setProperty(key, value);
	}

}
