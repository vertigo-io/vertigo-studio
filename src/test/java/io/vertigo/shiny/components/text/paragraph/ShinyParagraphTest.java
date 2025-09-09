package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;

public class ShinyParagraphTest {

    public static void main(final String[] args) {
        final ShinyWriter writer = Shiny.writer();
        Shiny.paragraph().text("This is a paragraph.").render(writer);
    }
}
