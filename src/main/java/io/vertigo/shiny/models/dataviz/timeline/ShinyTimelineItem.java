package io.vertigo.shiny.models.dataviz.timeline;

import io.vertigo.core.lang.Assertion;

public record ShinyTimelineItem(
        String title,
        String content,
        String color,
        String icon) {

    public ShinyTimelineItem {
        Assertion.check().isNotBlank(title, "Title cannot be blank");
    }
}
