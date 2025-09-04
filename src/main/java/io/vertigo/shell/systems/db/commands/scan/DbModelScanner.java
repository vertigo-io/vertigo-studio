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

	// Maximum Levenshtein distance for considering a match (e.g., 2 allows for small variations)
	private static final int MAX_LEVENSHTEIN_DISTANCE = 2;

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

		// Step 3: Check Levenshtein distance for close matches
		for (String keyword : SENSITIVE_KEYWORDS) {
			if (keyword.length() >= 3) { // Only check for keywords longer than 2 characters
				int distance = levenshteinDistance(lowerCaseColumnName, keyword);
				if (distance <= MAX_LEVENSHTEIN_DISTANCE && distance > 0) {
					return "levenshtein: " + keyword + " (distance: " + distance + ")";
				}
			}
		}

		return null; // Not sensitive
	}

	// Implementation of Levenshtein distance algorithm
	private static int levenshteinDistance(String s1, String s2) {
		int len1 = s1.length();
		int len2 = s2.length();
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		for (int i = 1; i <= len1; i++) {
			for (int j = 1; j <= len2; j++) {
				int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
				dp[i][j] = Math.min(
						Math.min(dp[i - 1][j] + 1, // Deletion
								dp[i][j - 1] + 1), // Insertion
						dp[i - 1][j - 1] + cost); // Substitution
			}
		}

		return dp[len1][len2];
	}
}
