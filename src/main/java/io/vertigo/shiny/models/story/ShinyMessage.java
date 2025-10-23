package io.vertigo.shiny.models.story;

import io.vertigo.core.lang.Assertion;
import java.time.Instant;

public record ShinyMessage(ShinyRole role, String content, Instant timestamp) {
    public ShinyMessage {
        Assertion.check()
            .isNotNull(role)
            .isNotBlank(content)
            .isNotNull(timestamp);
    }

    public static ShinyMessage of(final ShinyRole role, final String content) {
        // The assertions will be checked by the canonical constructor
        return new ShinyMessage(role, content, Instant.now());
    }
}
