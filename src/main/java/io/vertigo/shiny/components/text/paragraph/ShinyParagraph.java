package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyParagraph implements ShinyComponent {
    private final Shiny shiny;
    private final String text;

    public ShinyParagraph(final Shiny shiny, final String text) {
        Assertion.check().isNotNull(shiny);
        Assertion.check().isNotNull(text);
        //---
        this.shiny = shiny;
        this.text = text;
    }

    @Override
    public void print() {
        shiny.getWriter().println(text);
        shiny.getWriter().println(); // Add a blank line after the paragraph
    }
}
