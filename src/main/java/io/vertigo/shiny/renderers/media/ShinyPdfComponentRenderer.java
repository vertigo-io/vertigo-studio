package io.vertigo.shiny.renderers.media;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.media.pdf.ShinyPdfComponent;
import io.vertigo.shiny.renderers.ShinyModelRenderer;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyPdfComponentRenderer implements ShinyModelRenderer<ShinyPdfComponent> {

    @Override
    public boolean accept(final ShinyModel component) {
        return component instanceof ShinyPdfComponent;
    }

    @Override
    public void render(final ShinyPdfComponent pdfComponent) {
        Assertion.check().isNotNull(pdfComponent);
        //---
        final ShinyWriter writer = Shiny.writer();

        final String title = pdfComponent.title() != null ? pdfComponent.title() : "PDF Document";
        final String boxLine = "+".concat("-".repeat(title.length() + 2)).concat("+");

        writer.println(ShinyColors.YELLOW.fg(boxLine));
        writer.println(ShinyColors.YELLOW.fg("| " + title + " |"));
        writer.println(ShinyColors.YELLOW.fg(boxLine));

        writer.println("  Path: " + pdfComponent.pdfPath());
        writer.println("  Initial Page: " + pdfComponent.initialPage());
        writer.println("  Display Height: " + pdfComponent.height());
        writer.println(); // Add a blank line for spacing
    }
}
