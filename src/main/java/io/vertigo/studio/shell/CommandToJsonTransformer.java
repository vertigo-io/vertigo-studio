package io.vertigo.studio.shell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CommandToJsonTransformer {
	private final ObjectMapper objectMapper = new ObjectMapper();
	// Regex améliorée pour gérer les JSON imbriqués
	private static final Pattern COMMAND_PATTERN = Pattern.compile(
			"(create|merge)\\s+(\\w+)\\s+(\\w+)\\s+\\{([\\s\\S]*?)\\}$");

	public String transform(String command) throws JsonProcessingException {
		// Parser la commande
		Matcher matcher = COMMAND_PATTERN.matcher(command.trim());
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Commande invalide : " + command);
		}

		// Extraire les parties
		String operation = matcher.group(1); // create ou merge
		String type = matcher.group(2); // type de l'entité
		String id = matcher.group(3); // ID
		String jsonAttributes = "{" + matcher.group(4) + "}"; // Attributs JSON

		// Parser le JSON des attributs
		final ObjectNode attributes;
		try {
			attributes = (ObjectNode) objectMapper.readTree(jsonAttributes);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("JSON invalide dans la commande : " + jsonAttributes, e);
		}

		// Créer l'objet JSON final
		ObjectNode result = objectMapper.createObjectNode();
		result.put("command", operation);
		result.put("type", type);
		result.put("id", id);
		result.setAll(attributes);

		// Convertir en chaîne JSON formatée
		return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
	}

	public static void main(String[] args) {
		CommandToJsonTransformer transformer = new CommandToJsonTransformer();
		// Exemples avec JSON imbriqués
		String command = """
						create DtDefinition DtBase {
							"stereotype": "KeyConcept",
							"id" : { "code":"baseId", "domain":"DoId", "label":"Id"},
							"fields" : [
								{"name":"code", 		"domain":"DoCode",  "label":"Code", "cardinality":"1"},
								{"name":"name", 		"domain":"DoLabel",  "label":"Name", "cardinality":"1"},
								{"name":"description", "domain":"DoDescription",  "label":"Description" }
							]
						}
				""";

		System.out.println("Commande : " + command);
		try {
			System.out.println("JSON généré :\n" + transformer.transform(command));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("---");
	}
}
