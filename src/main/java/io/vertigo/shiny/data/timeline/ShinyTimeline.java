package io.vertigo.shiny.data.timeline;

import java.util.List;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTimeline(
        String title,
        List<ShinyTimelineItem> items) implements ShinyComponent {

    @Override
    public String type() {
        return "timeline";
    }
}
