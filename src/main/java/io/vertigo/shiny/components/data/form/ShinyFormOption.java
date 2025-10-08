package io.vertigo.shiny.components.data.form;

import io.vertigo.core.lang.Assertion;

public record ShinyFormOption(String label, Object value) {
    public ShinyFormOption {
        Assertion.check()
                .isNotBlank(label)
                .isNotNull(value);
    }
}