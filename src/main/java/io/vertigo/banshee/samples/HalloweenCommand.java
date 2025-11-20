package io.vertigo.banshee.samples;

import io.vertigo.shell.ai.Agent;
import io.vertigo.shell.ai.AgentBuilder;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.composer.ShinyComposer;

final class HalloweenCommand {
	private final Agent agent = new AgentBuilder().build();

	public ShinyModel llm(String Q) {
		final String query = """
								Crée un json conforme à l'exemple suivant :

								{
				    template: 'bar',
				    title: 'Performance Mensuelle',
				    labels: ['Avril', 'Mai', 'Juin'],
				    series: [
				        { name: 'Équipe A', data: [80, 90, 100] },
				        { name: 'Équipe B', data: [70, 85, 95] }
				    ]
				}

				Il est important que le json soit "valide" sans commentaires et respecte bien la structure json standard.
				Tu as le choix comme 'template' entre uniquement les 6 choix suivants : 'bar', 'pie', 'area', 'line', 'donut' ou  'radar'.
				Choisis le plus adapté des graphiques.
				Tu peux avoir plusieurs séries.

				Tu dois répondre en json à la question suivante :
				""" + Q;
		final String response = agent.answer(query);
		System.out.println("llm response >" + response);
		return new ShinyComposer().compose(response);
	}
}
