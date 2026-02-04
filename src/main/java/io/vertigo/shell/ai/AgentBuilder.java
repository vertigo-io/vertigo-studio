package io.vertigo.shell.ai;

import java.time.Duration;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import io.vertigo.shell.systems.env.Env;

public final class AgentBuilder {
	private static final double TEMPERATURE = 0.0;

	private static ChatModel buildChatModel() {
		return buildChatModel1();
	}

	private static ChatModel buildChatModel1() {
		return OpenAiChatModel
				.builder()
				.apiKey(Env.get(AgentVar.API_KEY))
				.timeout(Duration.ofSeconds(300))
				.modelName(Env.get(AgentVar.MODEL_NAME))
				.temperature(TEMPERATURE)
				.build();
	}

	//	private static ChatModel buildChatModel2() {
	//		return OllamaChatModel.builder()
	//				.baseUrl("http://localhost:11434") // ← Très important : Ollama écoute ici (localhost marche grâce au mirrored networking WSL)
	//				.modelName(Env.get(AgentVar.MODEL_NAME)) // ex: "qwen3:14b", "gemma3:9b", "llama3.2:8b", "gpt-oss:20b"...
	//				.temperature(TEMPERATURE)
	//				.timeout(Duration.ofSeconds(300)) // garde ton timeout long si tu utilises de gros modèles
	//				// .logRequests(true)                        // optionnel : très utile pour debugger
	//				// .logResponses(true)
	//				.build();
	//	}

	public Agent build() {
		final ChatModel chatModel = buildChatModel();
		final ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(20);

		return AiServices.builder(Agent.class)
				.chatModel(chatModel)
				.chatMemory(chatMemory)
				//.tools(new Tools())
				//				.systemMessageProvider((O) -> "Pour toute question avec un calcul de racine carrée, utilise l'outil tool enregsitré sous le nom 'calculateSquareRoot'")
				//	.systemMessageProvider((o) -> "Tu es un assistant qui répond aux questions sur l'actualité basées sur le flux RSS de la BBC. Voici le contexte : " + news)
				.build();
	}

}
