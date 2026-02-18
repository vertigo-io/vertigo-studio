package io.vertigo.banshee.samples;

import io.vertigo.banshee.com.BansheeCommandExecutor;
import io.vertigo.banshee.com.BansheeCommandLine;
import io.vertigo.shell.ai.Agent;
import io.vertigo.shell.ai.AgentBuilder;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.composer.ShinyComposer;

final class LLMCommandExecutor implements BansheeCommandExecutor {
	private final Agent agent = new AgentBuilder().build();

	public ShinyModel execute(BansheeCommandLine commandLine) throws Exception {
		final String query = """
				ANALYSE la question pour déterminer :
				1. Type de données : temporelles | catégorielles | pourcentages | multi-critères | géographiques
				2. Objectif : comparaison | évolution | répartition | profil | localisation
				3. Type de représentation : GRAPHIQUE ou CARTOGRAPHIQUE

				═══════════════════════════════════════════════════════════════

				FORMAT A - REPRÉSENTATIONS GRAPHIQUES (6 types) :

				{"template":"bar","title":"Titre du graphique","labels":["A","B","C"],"series":[{"name":"Série 1","data":[10,20,30]}]}

				Templates graphiques disponibles :
				• "bar" → Comparer des valeurs entre catégories (ex: ventes par région)
				• "line" → Évolution temporelle ou tendance (ex: CA mensuel)
				• "area" → Évolution avec volume cumulé (ex: croissance cumulative)
				• "pie" → Répartition en % d'un total - 1 SEULE série (ex: parts de marché)
				• "donut" → Répartition avec métrique centrale - 1 SEULE série (ex: budget)
				• "radar" → Comparaison multi-critères (ex: profils de compétences)

				Contraintes graphiques :
				- pie/donut : UNE série uniquement
				- autres : une ou plusieurs séries possibles
				- labels.length === data.length pour chaque série

				═══════════════════════════════════════════════════════════════

				FORMAT B - REPRÉSENTATION CARTOGRAPHIQUE (1 type) :

				{"template":"map","title":"Titre de la carte","geoPoints":[{"latitude":0.0,"longitude":0.0,"label":"nom de la ville"}]}

				Template cartographique :
				• "map" → Localisation géographique de points d'intérêt (ex: emplacements de magasins, sites touristiques)

				Contraintes cartographiques :
				- latitude : nombre décimal entre -90 et 90 (obligatoire)
				- longitude : nombre décimal entre -180 et 180 (obligatoire)
				- label : description du point (obligatoire)

				═══════════════════════════════════════════════════════════════

				Question : """ + commandLine.args() + """

				Réponds UNIQUEMENT avec le JSON (format A OU format B selon l'analyse).
				Pas de texte explicatif, pas de markdown, juste le JSON valide.
				""";
		System.out.println("llm query >" + query);
		final String response = agent.answer(query);
		System.out.println("llm response >" + response);
		return new ShinyComposer().compose(response);
	}
}
