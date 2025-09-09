package io.vertigo.shiny.components.text.markdown;

import java.io.IOException;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;

public class ShinyMarkDownTest {

    public static void main(final String[] args) throws IOException {
        final ShinyWriter writer = Shiny.writer();
        System.out.println("--- Testing Title ---");
        Shiny.markdown().fromText("# Title 1").render(writer);
        System.out.println("--- Testing List ---");
        Shiny.markdown().fromText("* item 1\n* item 2").render(writer);
        System.out.println("--- Testing Table ---");
        Shiny.markdown().fromText("| h1 | h2 |\n|---|---|\n| c1 | c2 |").render(writer);
        System.out.println("--- Testing Paragraph ---");
        Shiny.markdown().fromText("This is a paragraph.").render(writer);
    }
}