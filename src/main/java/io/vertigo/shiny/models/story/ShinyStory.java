package io.vertigo.shiny.models.story;

import io.vertigo.core.lang.Assertion;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record ShinyStory(List<ShinyMessage> messages) {

    public ShinyStory {
        // The canonical constructor receives the list. We make an immutable copy.
        Assertion.check().isNotNull(messages);
        messages = List.copyOf(messages); // List.copyOf provides an unmodifiable list
    }

    // Provides a public, static, empty story instance.
    public static final ShinyStory EMPTY = new ShinyStory(List.of());

    public ShinyStory withMessage(final ShinyMessage message) {
        Assertion.check().isNotNull(message);
        //---
        // Create a new list by concatenating the old list and the new message
        List<ShinyMessage> newMessages = Stream.concat(messages.stream(), Stream.of(message))
                                               .collect(Collectors.toList());
        return new ShinyStory(newMessages);
    }
}
