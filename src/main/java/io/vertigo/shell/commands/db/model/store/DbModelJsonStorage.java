package io.vertigo.shell.commands.db.model.store;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.vertigo.shell.commands.db.model.DbModel;

final class DbModelJsonStorage {
	private static final File STORAGE_FILE = new File("c:/Dev/vortex/jdbc-model.json");

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.enable(SerializationFeature.INDENT_OUTPUT); // JSON lisible

	/** Sauvegarde l'instance en JSON sur disque */
	static void save(final DbModel model) throws IOException {
		MAPPER.writeValue(STORAGE_FILE, model);
	}

	/** Recharge une instance depuis le fichier JSON */
	static DbModel load() throws IOException {
		return MAPPER.readValue(STORAGE_FILE, DbModel.class);
	}
}
