package io.vertigo.shiny.models.data.form;

import io.vertigo.core.lang.Assertion;

public record ShinyFormOption(String label, Object value) {
    public ShinyFormOption {
        Assertion.check()
                .isNotBlank(label)
                .isNotNull(value);
    }
}