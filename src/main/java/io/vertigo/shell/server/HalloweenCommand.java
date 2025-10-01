package io.vertigo.shell.server;

import io.vertigo.shell.ai.Agent;
import io.vertigo.shell.ai.AgentBuilder;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.composer.ShinyComposer;

public final class HalloweenCommand {
	private final Agent agent = new AgentBuilder().build();

	public ShinyComponent llm(String Q) {
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

				Tu as le choix comme template entre uniquement les 5 choix suivants : 'bar', 'pie', 'area', 'donut' et 'radar'.
				Choisis le plus adapté des graphiques.
				Tu peux avoir plusieurs séries.

				Tu dois répondre en json à la question suivante :
				""" + Q;
		final String response = agent.answer(query);
		System.out.println("llm response >" + response);
		return new ShinyComposer().compose(response);
	}
}
