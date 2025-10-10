package io.vertigo.shiny.components.data.chip;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyChip(
        String text,
        String color,
        ShinyChipVariant variant,
        boolean closable,
        String icon) implements ShinyComponent {

    @Override
    public String type() {
        return "chip";
    }

    public ShinyChip {
        Assertion.check()
                .isNotBlank(text, "Text cannot be blank");
    }
}
