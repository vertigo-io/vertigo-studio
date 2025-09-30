package io.vertigo.shiny.components.form;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record ShinyFormSection(String title, List<ShinyFormField> fields) {
    public ShinyFormSection {
        Assertion.check()
                .isNotBlank(title)
                .isNotNull(fields);
    }
}