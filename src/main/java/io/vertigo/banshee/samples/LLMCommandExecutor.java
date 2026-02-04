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
				Analyse d'abord la question suivante pour identifier :
				1. Type de données (temporelles, catégorielles, pourcentages, multi-critères ou cartographique)
				2. Objectif (comparer, montrer évolution, répartition, profil)
				3. on distingue les représentations graphiques ET LES représentations cartographiques
				4.a Pour les représentations graphiques UNIQUEMENT
				- Calcule les séries nécessaires
				- Génère un JSON valide selon ce format (sans commentaires) :
				{
				"template":"bar",
				"title":"Titre",
				"labels":["A","B"],
				"series":[{
					"name":"Série",
					"data":[1,2]
					}]
				}

				4.b Pour les représentations cartographiques UNIQUEMENT
				- Génère un JSON valide selon ce format (sans commentaires) :
				{
				"template":"map",
				"title":"Titre",
				"geoPoints":[{
					"latitude": 48.901022,
					"longitude":2.100765,
					"label":"Saint Germain en Laye"
					}]
				}

				5. CHOIX DU TEMPLATE selon l'analyse :
				Il y a 2 types de réprésentations
				On a 6 représentations graphiques
				- "bar" → Comparer valeurs entre catégories
				- "line" → Évolution/tendance temporelle
				- "area" → Évolution avec volume cumulé
				- "pie" → Répartition % (1 série uniquement)
				- "donut" → Répartition avec métrique centrale (1 série uniquement)
				- "radar" → Comparaison multi-critères/profils
				On a une représetation cartographique
				- "map" → Représentations de données cartographiques sous la forme de points d'intérêt

				Question : """ + commandLine.args() + """

				Réponds UNIQUEMENT avec le JSON final.
				""";
		final String response = agent.answer(query);
		System.out.println("llm response >" + response);
		return new ShinyComposer().compose(response);
	}
}
