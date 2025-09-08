package io.vertigo.shell.ai;

import java.time.Duration;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import io.vertigo.shell.systems.env.Env;

public final class AgentBuilder {
	private static final double TEMPERATURE = 0.0;

	private static ChatLanguageModel buildChatModel() {
		return OpenAiChatModel
				.builder()
				.apiKey(Env.get(AgentVar.API_KEY))
				.timeout(Duration.ofSeconds(300))
				.modelName(Env.get(AgentVar.MODEL_NAME))
				.temperature(TEMPERATURE)
				.build();
	}

	public Agent build() {
		final ChatLanguageModel chatModel = buildChatModel();
		final ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(20);

		return AiServices.builder(Agent.class)
				.chatLanguageModel(chatModel)
				.chatMemory(chatMemory)
				//.tools(new Tools())
				//				.systemMessageProvider((O) -> "Pour toute question avec un calcul de racine carrée, utilise l'outil tool enregsitré sous le nom 'calculateSquareRoot'")
				//	.systemMessageProvider((o) -> "Tu es un assistant qui répond aux questions sur l'actualité basées sur le flux RSS de la BBC. Voici le contexte : " + news)
				.build();
	}

}
