package io.vertigo.shell.systems.db.commands.scan;

import java.util.Arrays;
import java.util.List;

final class DbModelScanner {
	// Keywords for sensitive data detection (English and French, including simplified terms)
	private static final List<String> SENSITIVE_KEYWORDS = Arrays.asList(
			// English keywords
			"password", "pwd", "secret", "token", "api_key", "auth", "credential",
			"email", "mail", "e_mail",
			"ssn", "social_security_number", "nin", // National Identification Number
			"iban", "rib", "bank_account", "account_number", "credit_card", "cc_number", "card_number",
			"phone", "tel", "telephone", "mobile",
			"address", "street", "city", "zip", "postcode", "country",
			"birth_date", "dob",
			"name", "first_name", "last_name", "surname", "given_name",
			"gender", "sex",
			"health", "medical", "diagnosis",
			"fingerprint", "biometric",
			"salary", "income", "wage",
			// French keywords
			"mot_de_passe", "mdp", "motdepasse", "secret", "jeton", "clé_api", "authentification", "identifiant",
			"courriel", "email", "mail",
			"numéro_sécurité_sociale", "nir", // Numéro d'Inscription au Répertoire
			"iban", "rib", "compte_bancaire", "numéro_compte", "carte_bancaire", "numéro_carte",
			"téléphone", "tel", "mobile",
			"adresse", "rue", "ville", "code_postal", "pays",
			"date_naissance", "anniversaire",
			"nom", "prénom", "nom_famille",
			"genre", "sexe",
			"santé", "médical", "diagnostic",
			"empreinte_digitale", "biométrique",
			"salaire", "revenu");

	//	// Regex patterns for more complex matches (e.g., specific formats)
	//	private static final List<Pattern> SENSITIVE_PATTERNS = Arrays.asList(
	//			Pattern.compile(".*_id$", Pattern.CASE_INSENSITIVE), // Generic IDs might be sensitive if PII
	//			Pattern.compile(".*_uuid$", Pattern.CASE_INSENSITIVE) // Generic UUIDs might be sensitive if PII
	//	);

	static String sensitive(String columnName) {
		String lowerCaseColumnName = columnName.toLowerCase();

		// Step 1: Check exact keyword matches
		for (String keyword : SENSITIVE_KEYWORDS) {
			if (lowerCaseColumnName.contains(keyword)) {
				return "keyword: " + keyword;
			}
		}

		// Step 2: Check regex patterns
		//		for (Pattern pattern : SENSITIVE_PATTERNS) {
		//			if (pattern.matcher(lowerCaseColumnName).matches()) {
		//				return "pattern: " + pattern.pattern();
		//			}
		//		}
		return null; // Not sensitive
	}
}
