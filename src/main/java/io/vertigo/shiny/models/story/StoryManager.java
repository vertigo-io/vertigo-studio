package io.vertigo.shiny.models.story;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.lang.Assertion;

public class StoryManager {
	private static final String STORY_FILE_PATH = "story.json";
	private final ObjectMapper objectMapper;

	public StoryManager() {
		this.objectMapper = new ObjectMapper();
		//		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	public void save(final ShinyStory story) {
		Assertion.check().isNotNull(story);
		//---
		try {
			objectMapper.writeValue(new File(STORY_FILE_PATH), story);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public ShinyStory load() {
		final File storyFile = new File(STORY_FILE_PATH);
		if (storyFile.exists()) {
			try {
				return objectMapper.readValue(storyFile, ShinyStory.class);
			} catch (final IOException e) {
				e.printStackTrace();
				// If loading fails, return an empty story
				return ShinyStory.EMPTY;
			}
		}
		return ShinyStory.EMPTY;
	}
}
